package part2.virtualThread.search;

public interface SearchListener {
    void pageRequested(String page);
    void countUpdated(int count, String urlString);
}
