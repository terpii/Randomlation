package terpi;

public class Debug {

    public static byte verbosity = 2;
    public static byte devlevel = 10;

    public static void print(String toprint)
    {
        System.out.println(toprint);
    }

    public static void print(String toprint, byte level)
    {
        //level 0 is the quietest and 5 the loudest
        //level 10 is dev level debugging

        if(level >= verbosity)
        {
            print(toprint);
        }
    }

    public static void devprint(String toprint)
    {
        if(verbosity == devlevel)
        {
            print(toprint);
        }
    }

    public static byte getVerbosity() {
        return verbosity;
    }

    public static void setVerbosity(byte verbosity) {
        Debug.verbosity = verbosity;
    }

    public static byte getDevlevel() {
        return devlevel;
    }

    public static void setDevlevel(byte devlevel) {
        Debug.devlevel = devlevel;
    }
}
