package part2.reactiveProgramming.model.concurrent;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import kotlin.Pair;
import part2.reactiveProgramming.model.connection.Connection;
import part2.reactiveProgramming.model.connection.HtmlElement;
import part2.reactiveProgramming.model.connection.HttpsConnection;

import java.util.Optional;
import java.util.Scanner;

public class FlowableWorkerImpl implements FlowableWorker {

    private Connection connection;

    public FlowableWorkerImpl() {
        this.connection = new HttpsConnection();
    }

    private Flowable<HtmlElement> getLines(String url) {
        return Flowable.create(emitter -> {
            new Thread(() -> {
                Optional<Scanner> scanner = this.connection.getScannerConnection(url);
                if(scanner.isPresent()){
                    Scanner successScanner = scanner.get();
                    while(successScanner.hasNextLine()){
                        emitter.onNext(new HtmlElement(successScanner.nextLine()));
                    }
                    emitter.onComplete();
                }else{
                    //emitter.onError(new RuntimeException());
                    emitter.onComplete();
                }
            }).start();
        }, BackpressureStrategy.LATEST);
    }

    @Override
    public Flowable<String> getPageLinks(String url) {
        return this.getLines(url)
                   .map(HtmlElement::getLink)
                   .filter(Optional::isPresent)
                   .map(Optional::get);
    }

    @Override
    public Flowable<Pair<String, Integer>> getLinesWordCount(String url, String word) {
        return this.getLines(url)
                .filter(e -> !e.getText().isEmpty())
                .map(e -> new Pair<>(url, e.wordCount(word)));
    }
}
