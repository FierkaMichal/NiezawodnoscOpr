public class CCSMRunner {

    public static String execCmd(String cmd) throws java.io.IOException {
        java.util.Scanner s = new java.util.Scanner(Runtime.getRuntime().
                exec(cmd).
                getInputStream()).
                useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
