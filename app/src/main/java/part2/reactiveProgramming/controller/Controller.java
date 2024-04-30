package part2.reactiveProgramming.controller;

public interface Controller {
    void startSearch(String url, String word, int depth);
    void stopSearch();
    void updateGUI();
}
