package part2.reactiveProgramming.controller;

import part2.reactiveProgramming.model.ConcurrentReader;
import part2.reactiveProgramming.model.Reader;

public class ControllerImpl implements Controller{
    private Reader reader;

    public ControllerImpl(){
        this.reader = new ConcurrentReader();
    }

    @Override
    public void startSearch(String url, String word, int depth) {
    }

    @Override
    public void stopSearch() {

    }

    @Override
    public void updateGUI() {

    }
}
