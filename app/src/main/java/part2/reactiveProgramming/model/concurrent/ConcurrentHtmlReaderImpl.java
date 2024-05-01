package part2.reactiveProgramming.model.concurrent;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import kotlin.Pair;

import java.util.HashSet;
import java.util.Set;

public class ConcurrentHtmlReaderImpl implements ConcurrentHtmlReader{

    private FlowableWorker flowableWorker;
    private Subject<Flowable<Pair<String, Integer>>> linesProcessor;
    private Subject<Flowable<String>> linkProcessor;
    private Set<String> linksAlredyProcessed;

    public ConcurrentHtmlReaderImpl() {
        this.flowableWorker = new FlowableWorkerImpl();
        this.linesProcessor = PublishSubject.create();
        this.linkProcessor = PublishSubject.create();
        this.linksAlredyProcessed = new HashSet<>();
    }

    @Override
    public void getWordCount(String url, String word, int depth) {
        if(depth > 0 && !linksAlredyProcessed.contains(url)){
            this.linksAlredyProcessed.add(url);
            this.linkProcessor.onNext(this.flowableWorker.getPageLinks(url));
            this.linesProcessor.onNext(this.flowableWorker.getLinesWordCount(url, word));
            //Todo: Recursive iteration
        }
    }

    @Override
    public void handlerNewLinkFound() {
        this.linkProcessor.forEach(e -> e.subscribe(l -> {
            System.out.println("[Link]: " + l);
        }));
    }

    @Override
    public void handlerNewLineProcessed() {
        this.linesProcessor.forEach(e -> e.subscribe(l -> System.out.println("[Link in line]: " + l.component1() + " [Count]: " + l.component2())));
    }

}
