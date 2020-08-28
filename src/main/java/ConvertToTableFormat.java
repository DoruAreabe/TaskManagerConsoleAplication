import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConvertToTableFormat {
    public static void main(String[] args) {
        List<String> l = new ArrayList<>();
        l.add("Write some code                 2020-09-15  true");
        String a = l.get(0);
        String [] b = new String[3];
        b[0]=a.substring(0,30).trim();
        b[1]=a.substring(31,42).trim();
        b[2]=a.substring(43).trim();
        System.out.println(Arrays.toString(b));
        Path path = Paths.get("test.scv");
        List<String> result = new ArrayList<>();
        result.add(b[0]+", "+b[1]+", "+b[2]);
        try {
            Files.write(path, result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
