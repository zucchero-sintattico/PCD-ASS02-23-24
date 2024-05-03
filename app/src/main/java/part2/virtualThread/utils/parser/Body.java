package part2.virtualThread.utils.parser;

import java.util.stream.Stream;

public abstract class Body<T> {

    protected final T body;

    public Body(T body) {
        this.body = body;
    }

    public abstract Stream<String> getLinks();

    public abstract Stream<String> getWords();

    protected String[] getWords(String text) {
        return text.toLowerCase().split(" ");
    }

}
