package part2.reactiveProgramming.model;

import io.reactivex.rxjava3.core.Observable;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.Future;

public class ConcurrentReader {

    public Observable<String> getAllLines(String url){
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
                    throw new RuntimeException(e);
                } finally {
                    InputStream finalInputStream = inputStream;
                    emitter.setCancellable(() -> {
                        finalInputStream.close();
                    });
                }
            }).start();
        });
    }

    public Observable<String> getAllLinks(String url){
        return Observable.create(emitter -> {
            new Thread(() -> {
                InputStream inputStream = null;
                try {
                    inputStream = new URI(url).toURL().openStream();
                    Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8);
                    while (scanner.hasNextLine()) {
                        String nextElement = Jsoup.parse(scanner.nextLine()).select("a").attr("href");
                        if(nextElement.startsWith("https")){
                            emitter.onNext(nextElement);
                        }
                    }
                    emitter.onComplete();
                } catch (IOException | URISyntaxException e) {
                    throw new RuntimeException(e);
                } finally {
                    InputStream finalInputStream = inputStream;
                    emitter.setCancellable(() -> {
                        finalInputStream.close();
                    });
                }
            }).start();
        });
    }
}