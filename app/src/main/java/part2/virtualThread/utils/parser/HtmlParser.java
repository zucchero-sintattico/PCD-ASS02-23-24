package part2.virtualThread.utils.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class HtmlParser {

    private static List<String> extensionToFilter = new ArrayList<>();
    private final String[] words;

    public static void findLinks(String word, Consumer<String> consumer){
        if(word.trim().contains("href=\"https://")){
            String href = word.split("\"")[1];
            if(extensionToFilter.stream().noneMatch(href::endsWith)){
                consumer.accept(href);
            }
        }
    }

    public static void match(String occurrence, String word, Consumer<String> consumer){
        if(occurrence.trim().equals(word)){
            consumer.accept(occurrence);
        }
    }

    public static void addExtensionToFilter(String... extension){
        extensionToFilter = Arrays.asList(extension);
    }

    private HtmlParser(String[] words) {
        this.words = words;
    }

    public static HtmlParser parse(String html){
        String[] words = html.toLowerCase().split(" ");
        return new HtmlParser(words);
    }

    public HtmlParser foreach(Consumer<String> consumer){
        for (String word : words) {
            consumer.accept(word);
        }
        return this;
    }

    public HtmlParser doAction(Runnable runnable){
        runnable.run();
        return this;
    }


}
