package part2.reactiveProgramming.controller;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observer;
import kotlin.Pair;
import part2.reactiveProgramming.model.concurrent.ConcurrentHtmlReader;
import part2.reactiveProgramming.model.concurrent.ConcurrentHtmlReaderImpl;

public class ControllerImpl {

    private ConcurrentHtmlReader reader;

    public ControllerImpl() {
        this.reader = new ConcurrentHtmlReaderImpl();
    }

    public void start(String url, String word, int depth){
        this.reader.getWordCount(url, word, depth);
    }

    public void attachSubscriberForNewLinks(Observer<Flowable<String>> observer){
        this.reader.handlerNewLinkFound(observer);
    }

    public void attachSubscriberForNewLineProcess(Observer<Flowable<Pair<String, Integer>>> observer){
        this.reader.handlerNewLineProcessed(observer);
    }
}
