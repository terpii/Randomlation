package terpi.api;

import org.apache.http.HttpResponse;
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
}
