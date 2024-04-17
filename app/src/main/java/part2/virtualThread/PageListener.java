package part2.virtualThread;

public interface PageListener {
    void pageRequested(String page);
    void countUpdated(int count, String urlString);
}
