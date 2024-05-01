package part2.reactiveProgramming.controller;

import io.reactivex.rxjava3.core.Observer;
import part2.reactiveProgramming.model.ConcurrentReader;
import part2.reactiveProgramming.model.Reader;

import java.util.concurrent.ExecutionException;

public class ControllerImpl implements Controller{
    private Reader reader;

    public ControllerImpl(){
        this.reader = new ConcurrentReader();
    }

    @Override
    public void startSearch(String url, String word, int depth) throws ExecutionException, InterruptedException {
        this.reader.counter(url, word, depth);
    }

    @Override
    public void subscribe(Observer<String> observer) {
        this.reader.subscribe(observer);
    }

    @Override
    public void stopSearch() {

    }
}
