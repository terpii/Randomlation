package terpi.helpers;

import java.io.File;

public class PathHelper {

    //function will return path for any os
    public static String getPath(String path)
    {
        char pathchar = File.separatorChar;
        String outpath = path.replace('/', pathchar);
        outpath = outpath.replace('\\', pathchar);
        return outpath;
    }
}
