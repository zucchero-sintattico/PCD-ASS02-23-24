package part2.virtualThread.utils;

import part2.virtualThread.utils.parser.HtmlParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Configuration {

    public static void setup(){
        try {
            List<String> lines = Files.readAllLines(Paths.get("app/src/main/java/part2/resources/file/ExtensionToExclude.txt"));
            HtmlParser.addExtensionToFilter(lines.toArray(String[]::new));
        } catch (IOException e) {
            System.out.println("Failed to load extension to exclude file");
        }
    }
}
