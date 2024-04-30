package part2.reactiveProgramming.model;

import java.util.Set;
import java.util.concurrent.Future;

public interface Reader {
    Future<Long> getWordCount(String url, String word);
    Future<Set<String>> getAllPageLinks(String url);
}
