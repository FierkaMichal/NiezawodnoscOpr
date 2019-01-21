import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileCodeReader {

    private File file;

    public FileCodeReader(File file) {
        this.file = file;
    }

    public String getCodeInString() {
        Scanner sc = null;
        String code = null;
        try {
            sc = new Scanner(file);
            code = sc.useDelimiter("\\A").next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            sc.close();
        }
        return code;
    }
}
