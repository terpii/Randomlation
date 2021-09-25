package terpi.api;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import terpi.Debug;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class API {

    private static final CloseableHttpClient httpClient = HttpClients.createDefault();


    public static String getTable(String url, String tableid) throws IOException {

        HttpPost postRequest = new HttpPost(url);
        StringEntity input = null;

        try {
            input = new StringEntity("{\"seriesid\":[\"" + tableid + "\"]}");
        } catch (UnsupportedEncodingException e) {
            Debug.print("Unsupported Encoding Exception while trying to set StringEntity for request to " + url + " with tableid of " + tableid, (byte) 5);
            e.printStackTrace();
        }

        assert input != null;
        input.setContentType("application/json");
        postRequest.setEntity(input);

        HttpResponse response;

        try {
            response = httpClient.execute(postRequest);
        } catch (IOException e) {
            Debug.print("IOException while trying to execute " + postRequest);
            e.printStackTrace();
            return null;
        }

        return new String(response.getEntity().getContent().readAllBytes());
    }

    public static String getTables(String url, String[] tableids) throws IOException {
        HttpPost postRequest = new HttpPost(url);

        //Convert the Array to Json
        JSONArray tableids_json = new JSONArray();
        Collections.addAll(tableids_json, tableids);

        StringEntity input = null;

        try {
            input = new StringEntity("{\"seriesid\":" + tableids_json + "}");
        } catch (UnsupportedEncodingException e) {
            Debug.print("Unsupported Encoding Exception while trying to set StringEntity for request to " + url + " with tableids of " + Arrays.toString(tableids), (byte) 5);
            e.printStackTrace();
        }

        assert input != null;
        input.setContentType("application/json");
        postRequest.setEntity(input);

        HttpResponse response;

        try {
            response = httpClient.execute(postRequest);
        } catch (IOException e) {
            Debug.print("IOException while trying to execute " + postRequest);
            e.printStackTrace();
            return null;
        }

        return new String(response.getEntity().getContent().readAllBytes());
    }

    public static String getTitle(String id) throws IOException
    {
        HttpGet get = new HttpGet("https://data.bls.gov/timeseries/" + id);

        HttpResponse response = httpClient.execute(get);

        String content = new String(response.getEntity().getContent().readAllBytes());


        //The web they have is wonky, so we have to use lots of methods
        //This was a pain to implement, 1 Start 1 prayer

        //method 1: they have a title, so we extract that using a pattern; example: WSU020
        Pattern titlePat = Pattern.compile("<tr class=\"catalog\"><th class=\"catalog\" scope=\"row\">Series Title:</th><td class=\"catalog\">(.*?)</td></tr>");
        Matcher tilteMatch = titlePat.matcher(content);
        if(tilteMatch.find())
        {
            return tilteMatch.group(1);
        }
        //remove the Pattern and Matcher from memory because we don't need the anymore
        titlePat = null;
        tilteMatch = null;

        //method 2: they don't have a title, but a data type, state and
        //industry, so we extract those and combine them into a title
        Pattern statePat = Pattern.compile("<tr class=\"catalog\"><th class=\"catalog\" scope=\"row\">State:</th><td class=\"catalog\">(.*?)</td></tr>");
        Matcher stateMatch = statePat.matcher(content);

        Pattern indPat = Pattern.compile("<tr class=\"catalog\"><th class=\"catalog\" scope=\"row\">Industry:</th><td class=\"catalog\">(.*?)</td></tr>");
        Matcher indMatch = indPat.matcher(content);

        Pattern dataPat = Pattern.compile("<tr class=\"catalog\"><th class=\"catalog\" scope=\"row\">Data Type:</th><td class=\"catalog\">(.*?)</td></tr>");
        Matcher dataMatch = dataPat.matcher(content);

        if(stateMatch.find() && indMatch.find() && dataMatch.find())
        {
            String state = stateMatch.group(1);
            String industry = indMatch.group(1);
            String data = dataMatch.group(1);

            return industry + " " + data + " in " + state;//this is gonna sound horrible, but you can go look for who cares
        }
        //fuck these variables
        statePat = dataPat = null;
        stateMatch = dataMatch =  null;


        //method 3: certified bruh moment, more combining but different, here's an example: https://data.bls.gov/timeseries/PRS30006152
        Pattern secPattern = Pattern.compile("<tr class=\"catalog\"><th class=\"catalog\" scope=\"row\">Sector:</th><td class=\"catalog\">(.*?)</td></tr>");
        Matcher secMatcher = secPattern.matcher(content);

        Pattern mesPattern = Pattern.compile("tr class=\"catalog\"><th class=\"catalog\" scope=\"row\">Measure:</th><td class=\"catalog\">(.*?)</td></tr>");
        Matcher mesMatcher = secPattern.matcher(content);

        Pattern durPattern = Pattern.compile("<tr class=\"catalog\"><th class=\"catalog\" scope=\"row\">Duration:</th><td class=\"catalog\">(.*?)</td></tr>");
        Matcher durMatcher = durPattern.matcher(content);

        Pattern headPattern = Pattern.compile("<H4>(.*?)</H4>");
        Matcher headMatcher = headPattern.matcher(content);

        if(secMatcher.find() && mesMatcher.find() && durMatcher.find() && headMatcher.find())
        {
            String sector = secMatcher.group(1);
            String measurement = mesMatcher.group(1);
            String duration = durMatcher.group(1);
            String header = headMatcher.group(1);

            return header + " in " + sector + " in " + measurement + " in " + duration;//another stroke while reading the outcome
        }
        //nobody likes these variables
        secPattern = mesPattern = durPattern = null;
        secMatcher = mesMatcher = durMatcher = null;


        //method 4: just use header 4 as a title and add the area
        Pattern areaPattern = Pattern.compile("<tr class=\"catalog\"><th class=\"catalog\" scope=\"row\">Area:</th><td class=\"catalog\">(.*?)</td></tr>");
        Matcher areaMatcher = areaPattern.matcher(content);

        if(headMatcher.find() && areaMatcher.find())
        {
            return headMatcher.group(1) + " in " + areaMatcher.group(1);
        }
        //all my homies hate unused variables
        areaPattern = null;
        areaMatcher = null;


        //method 5: header 4 and Characteristic
        Pattern charPattern = Pattern.compile("<tr class=\"catalog\"><th class=\"catalog\" scope=\"row\">Characteristic:</th><td class=\"catalog\">(.*?)</td></tr>");
        Matcher charMatcher = charPattern.matcher(content);

        if(headMatcher.find() && charMatcher.find())
        {
            return headMatcher.group(1) + " : " + charMatcher.group(1);
        }
        //it's time for you to go home
        charPattern = null;
        charMatcher = null;

        //method 6: h4 and case type:
        Pattern casePattern = Pattern.compile("<tr class=\"catalog\"><th class=\"catalog\" scope=\"row\">Case Type:</th><td class=\"catalog\">(.*?)</td></tr>");
        Matcher caseMatcher = casePattern.matcher(content);

        if(caseMatcher.find() && headMatcher.find())
        {
            return headMatcher.group(1) + " : " + caseMatcher.find();
        }
        //no
        casePattern = null;
        caseMatcher = null;


        //method 7: h4 and industry
        if(indMatch.find() && headMatcher.find())
        {
            return headMatcher.group(1) + " : " + indMatch.group(1);
        }


        //method 8: only using header 4
        if(headMatcher.find())
        {
            return headMatcher.group(1);
        }

        Debug.devprint("No title found for " + id + "\nHow? i don't get it either");
        return null;
    }
}
