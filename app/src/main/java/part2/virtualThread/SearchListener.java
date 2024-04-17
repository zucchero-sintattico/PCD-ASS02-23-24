package part2.virtualThread;

public interface SearchListener {
    void pageRequested(String page);
    void countUpdated(int count, String urlString);
}
