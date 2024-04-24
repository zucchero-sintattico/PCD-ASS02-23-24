package part2.virtualThread.utils.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class HtmlParser<T> {

    public static List<String> extensionToFilter = Collections.unmodifiableList(new ArrayList<>());

    public static final String linkToMatch = "https://.*";
//    public static final String linkToMatch = "https*://.*";

    public static void addExtensionToFilter(String... extension){
        extensionToFilter = Collections.unmodifiableList(Arrays.asList(extension));
    }

    public static<T> HtmlParser<T> parse(Body<T> body){
        return new HtmlParser<>(body);
    }

    private final Body<T> body;

    private HtmlParser(Body<T> body) {
        this.body = body;
    }

    public HtmlParser<T> doAction(Runnable runnable){
        runnable.run();
        return this;
    }

    public HtmlParser<T> foreachLink(Consumer<String> consumer){
        body.getLinks().forEach(consumer);
        return this;
    }

    public HtmlParser<T> foreachWord(Consumer<String> consumer){
        body.getWords().forEach(consumer);
        return this;
    }


}
