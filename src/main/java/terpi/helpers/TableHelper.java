package terpi.helpers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import terpi.Debug;

import java.util.*;

public class TableHelper {

    public static float[] getValues(String jsonstr)
    {
        Object obj;
        try {
            obj = new JSONParser().parse(jsonstr);
        } catch (ParseException e) {
            Debug.print("JSON parse exception");
            e.printStackTrace();
            return null;
        }

        JSONObject jo = (JSONObject) obj;
        JSONObject jsonArray = (JSONObject)jo.get("Results");
        JSONArray jSeriesArray = (JSONArray)jsonArray.get("series");
        JSONObject jDataObject = (JSONObject) jSeriesArray.get(0);
        JSONArray jDataArray = (JSONArray) jDataObject.get("data");
        Debug.print(jDataArray.toString(), (byte) 10);

        Iterator itr = jDataArray.iterator();

        List<String> sValueArray = new ArrayList<>();

        while (itr.hasNext())
        {
            sValueArray.add((String) ((Map)itr.next()).get("value"));
        }

        float[] fValueArray = new float[sValueArray.size()];

        for(int i = 0; i < sValueArray.size(); i++)
        {
            fValueArray[i] = Float.parseFloat(sValueArray.get(i));
        }

        return fValueArray;
    }

    public static float[][] getValueMultiTable(String jsonstr)
    {
        Object obj;
        try {
            obj = new JSONParser().parse(jsonstr);
        } catch (ParseException e) {
            Debug.print("JSON parse exception");
            e.printStackTrace();
            return null;
        }

        JSONObject jo = (JSONObject) obj;
        JSONObject jsonArray = (JSONObject)jo.get("Results");
        JSONArray jSeriesArray = (JSONArray)jsonArray.get("series");

        float[][] fValueArray = new float[jSeriesArray.size()][];

        Iterator seriesItr = jSeriesArray.iterator();
        int seriesIntItr = 0;
        while (seriesItr.hasNext())
        {
            JSONObject jDataObject = (JSONObject) seriesItr.next();
            JSONArray jDataArray = (JSONArray) jDataObject.get("data");
            Debug.print(jDataArray.toString(), (byte) 10);

            Iterator itr = jDataArray.iterator();

            List<String> sValueArray = new ArrayList<>();

            while (itr.hasNext())
            {
                sValueArray.add((String) ((Map)itr.next()).get("value"));
            }

            float[] fValueSubArray = new float[sValueArray.size()];

            for(int i = 0; i < sValueArray.size(); i++)
            {
                fValueSubArray[i] = Float.parseFloat(sValueArray.get(i));
            }

            fValueArray[seriesIntItr] = fValueSubArray;
            seriesIntItr++;
        }



        return fValueArray;
    }
}
