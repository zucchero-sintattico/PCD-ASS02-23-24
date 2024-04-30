package part2.reactiveProgramming.model;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class ConcurrentReader implements Reader{

    private ReportListener listener;

    public ConcurrentReader(ReportListener listener){
        this.listener = listener;
    }

    private Observable<String> getAllLines(String url){
        return Observable.create(emitter -> {
            new Thread(() -> {
                InputStream inputStream = null;
                try {
                    inputStream = new URI(url).toURL().openStream();
                    Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8);
                    while (scanner.hasNextLine()) {
                        emitter.onNext(scanner.nextLine());
                    }
                    emitter.onComplete();
                } catch (IOException | URISyntaxException e) {
                    emitter.onError(e);
                } finally {
                    InputStream finalInputStream = inputStream;
                    emitter.setCancellable(() -> {
                        assert finalInputStream != null;
                        finalInputStream.close();
                    });
                }
            }).start();
        });
    }

    private Observable<String> getAllLinks(String url){
        return this.getAllLines(url)
                   .map(e -> Jsoup.parse(e).select("a").attr("href"))
                   .observeOn(Schedulers.computation())
                   .filter(e -> e.startsWith("https"));
    }

    public Future<Long> getWordCount(String url, String word){
        return this.getAllLines(url)
                .map(e -> Jsoup.parse(e).body().text())
                .filter(e -> e.contains(word))
                .observeOn(Schedulers.computation())
                .count()
                .toFuture();
    }

    public Future<Set<String>> getAllPageLinks(String url){
        return this.getAllLinks(url).collect(Collectors.toSet()).toFuture();
    }


    public void counter(String url, String word, int depth) throws InterruptedException, ExecutionException {
        var count = this.getWordCount(url, word).get();
        var links = this.getAllPageLinks(url).get().stream().limit(depth).collect(Collectors.toSet());
        this.listener.wordFounded(url, links, count);
        System.out.println(url + "\n[Count]: " + count + "\n[Links]: " + links);
        if(depth >= 0){
            links.forEach(link -> {
                try {
                    int currentDepth = depth - 1;
                    counter(link, word, currentDepth);
                } catch (ExecutionException | InterruptedException e) {
                    System.out.println("[Error]: " + e.getMessage());
                }
            });
        }
    }

}