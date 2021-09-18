package terpi;

import terpi.api.API;
import terpi.api.TableIdsManager;
import terpi.helpers.PathHelper;
import terpi.helpers.TableHelper;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;


public class Main {
    public static final String  url = "https://api.bls.gov/publicAPI/v1/timeseries/data/";

    public static void main(String[] args) throws IOException {
        Debug.print("randomlation started!");

        init();

        Debug.print("Starting test on multi table data extraction");

        TableIdsManager tableIdsManager = new TableIdsManager();

        tableIdsManager.readTableIds(PathHelper.getPath("src/main/resources/seriesids"));
        String jsonrepsonse = API.getTables(url, new String[] {tableIdsManager.getRandomTableID(), tableIdsManager.getRandomTableID()});
        Debug.print(jsonrepsonse);


        for (float[] arr: Objects.requireNonNull(TableHelper.getValueMultiTable(jsonrepsonse)))
        {
            Debug.print(Arrays.toString(arr));
        }
    }

    private static void init()
    {
        Debug.setVerbosity((byte) 10);
    }
}
