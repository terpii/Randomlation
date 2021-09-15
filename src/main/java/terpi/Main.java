package terpi;

public class Main {
    public static void main(String[] args)
    {
        Debug.print("randomlation started!");

        init();
    }

    private static void init()
    {
        Debug.setVerbosity((byte) 10);
    }
}
