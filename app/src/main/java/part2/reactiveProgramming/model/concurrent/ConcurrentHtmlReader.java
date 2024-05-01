package part2.reactiveProgramming.model.concurrent;

public interface ConcurrentHtmlReader {
    void getWordCount(String url, String word, int depth);
    void handlerNewLinkFound();
    void handlerNewLineProcessed();
}
