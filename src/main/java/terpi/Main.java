package terpi;

import terpi.api.API;
import terpi.api.TableIdsManager;
import terpi.helpers.PathHelper;

import java.util.Arrays;


public class Main {


    public static void main(String[] args)
    {
        Debug.print("randomlation started!");

        init();

        Debug.print("Attempting to get TableIds from Files");

        TableIdsManager tableIdsManager = new TableIdsManager();

        tableIdsManager.readTableIds(PathHelper.getPath("src/main/resources/seriesids"));

        while (tableIdsManager.randHasNext())
        {
            Debug.print(tableIdsManager.getRandomTableID());
        }
    }

    private static void init()
    {
        Debug.setVerbosity((byte) 10);
    }
}
