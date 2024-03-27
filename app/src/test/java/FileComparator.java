import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class FileComparator {
    public static boolean compareFiles(String path1, String path2) {



        try {
            byte[] file1 = Files.readAllBytes(Paths.get(path1).getFileName());
            byte[] file2 = Files.readAllBytes(Paths.get(path2).getFileName());

            return Arrays.equals(file1, file2);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
