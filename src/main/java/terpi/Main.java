package terpi;

import terpi.api.API;

import java.io.IOException;

public class Main {
    public static void main(String[] args)
    {
        Debug.print("randomlation started!");

        init();

        Debug.print("Attempting multiple tables test request...");
        try {
            Debug.print(API.getTables("https://api.bls.gov/publicAPI/v1/timeseries/data/", new String[]{"LAUCN040010000000005", "LAUCN040010000000006"}));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void init()
    {
        Debug.setVerbosity((byte) 10);
    }
}
