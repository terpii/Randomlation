package terpi;

import terpi.api.API;

import java.io.IOException;

public class Main {
    public static void main(String[] args)
    {
        Debug.print("randomlation started!");

        init();

        Debug.print("Attempting test request...");
        try {
            Debug.print(API.getTable("https://api.bls.gov/publicAPI/v1/timeseries/data/", "SMU19197802023800001"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void init()
    {
        Debug.setVerbosity((byte) 10);
    }
}
