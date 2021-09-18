package terpi.api;

import terpi.Debug;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class TableIdsManager {

    public List<String> tableids = new ArrayList<>();
    private List<String> tableidsback = new ArrayList<>();//this is a list with the random tables we already selected removed
    private int idIndex = 0;
    public boolean hasNext = true;

    public void readTableIds(String file)
    {
        File idFile = new File(file);
        Scanner idScanner;
        try {
            idScanner = new Scanner(idFile);
        } catch (FileNotFoundException e) {
            Debug.print("Table id file not found");
            e.printStackTrace();
            return;
        }
        while(idScanner.hasNextLine())
        {
            tableids.add(idScanner.nextLine());
        }
        tableidsback = tableids;
        idScanner.close();
    }

    public List<String> getTableids() {
        return tableids;
    }

    //get a table id in order
    public String getTableId()
    {
        String tableId = tableids.get(idIndex);
        idIndex++;
        return tableId;
    }

    //doesn't select things twice
    public String getRandomTableID()
    {
        int random = (new Random().nextInt(tableidsback.size()));
        String randomTableId = tableidsback.get(random);
        tableidsback.remove(random);
        return randomTableId;
    }

    //has next checks if there's a tableid beyond the one we're on right now, use with a while loop and getTableId to get all table id's
    public boolean hasNext() {
        return hasNext;
    }

    public boolean randHasNext()
    {
        return !tableidsback.isEmpty();
    }

    private void updateHasNext()
    {
        hasNext = idIndex < tableids.size();
    }
}
