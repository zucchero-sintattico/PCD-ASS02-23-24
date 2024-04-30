package part2.reactiveProgramming;

import part2.reactiveProgramming.model.ConcurrentReader;
import part2.reactiveProgramming.model.Reader;
import part2.reactiveProgramming.view.GUI;

import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //new GUI().display();
        ConcurrentReader reader = new ConcurrentReader();
        reader.counter("https://en.wikipedia.org", "wikipedia", 5);
    }
}
