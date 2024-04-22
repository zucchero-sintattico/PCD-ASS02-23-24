package part2.virtualThread.utils.parser;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class HtmlParserJSoup {

    private static List<String> extensionToFilter = new ArrayList<>();
    private final Document words;

    public static void findLinks(String word, Consumer<String> consumer){
        if(word.trim().contains("href=\"https://")){
            String href = word.split("\"")[1];
            if(extensionToFilter.stream().noneMatch(href::endsWith)){
                consumer.accept(href);
            }
        }
    }

    public static void match(String occurrence, String word, Consumer<String> consumer){
        if(occurrence.trim().toLowerCase().equals(word)){
            consumer.accept(occurrence);
        }
    }

    public static void addExtensionToFilter(String... extension){
        extensionToFilter = Arrays.asList(extension);
    }

    private HtmlParserJSoup(Document words) {
        this.words = words;
    }

    public static HtmlParserJSoup parse(Document words){
        return new HtmlParserJSoup(words);
    }

    public HtmlParserJSoup foreachLink(Consumer<String> consumer){
        words.select("a").forEach(link -> consumer.accept(link.attr("href")));
//        for (String word : words) {
//            consumer.accept(word);
//        }
        return this;
    }

    public HtmlParserJSoup foreachWord(Consumer<String> consumer){
//        words.select("p").forEach(link -> {
//            consumer.accept(link.attr("href"))
//        });
//
        for (String word : words.body().text().split(" ")) {
            consumer.accept(word);
        }
        return this;
    }

    public HtmlParserJSoup doAction(Runnable runnable){
        runnable.run();
        return this;
    }


}
