package part2.reactiveProgramming;

import part2.reactiveProgramming.model.concurrent.ConcurrentHtmlReaderImpl;
import part2.reactiveProgramming.view.GUI;

import javax.swing.*;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SwingUtilities.invokeLater(() -> {
            new GUI().display();
        });
        //ConcurrentReader reader = new ConcurrentReader();
        //reader.counter("https://en.wikipedia.org", "wikipedia", 5);
        //ConcurrentHtmlReaderImpl reader = new ConcurrentHtmlReaderImpl();
        //reader.getWordCount("https://en.wikipedia.org", "wikipedia", 2);
    }
}
