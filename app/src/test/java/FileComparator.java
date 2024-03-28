import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileComparator {
    public static boolean compareFiles(String path1, String path2) {

        try {
            List<String> file1 = new BufferedReader(new FileReader(path1)).lines().toList();
            List<String> file2 = new BufferedReader(new FileReader(path2)).lines().toList();
            return file1.equals(file2);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
}
