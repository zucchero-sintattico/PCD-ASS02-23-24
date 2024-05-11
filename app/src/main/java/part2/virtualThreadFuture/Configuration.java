package part2.virtualThreadFuture;

import part2.virtualThreadFuture.state.LogType;
import part2.utils.parser.HtmlParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class Configuration {

    public static final Boolean SAFE_SEARCH = true;

    public static final Boolean VISIT_SAME_LINK = true;

    public static final Boolean USE_DEFAULTS = true;

    public static final int GUI_UPDATE_MS = 32;

    public static final int STATE_UPDATE_MS = 8;

    private static final String DEFAULT_ROOT = "https://www.google.com/";

    private static final int DEFAULT_DEPTH = 2;

    private static final String DEFAULT_WORD = "google";

    private static final String excludedExtensionPath = "app/src/main/java/part2/resources/file/ExtensionToExclude.txt";

    public static void setup(){
        try {
            List<String> lines = Files.readAllLines(Paths.get(excludedExtensionPath));
            HtmlParser.addExtensionToFilter(lines.toArray(String[]::new));
        } catch (IOException e) {
            System.out.println("Failed to load extension to exclude file");
        }
    }

    public static Map<LogType, Boolean> getLogPolicy() {
        return Map.of(
                LogType.UPDATE, true,
                LogType.INFO, false,
                LogType.ERROR, false
        );
    }
    
    public static String defaultRoot() {
        return DEFAULT_ROOT;
    }

    public static String defaultDepth() {
        return DEFAULT_DEPTH+"";
    }

    public static String defaultWord() {
        return DEFAULT_WORD;
    }

}
