package part2.reactiveProgramming.model;

import java.util.Optional;

public interface Html {

    String getHtml();
    String getText();
    boolean isLink();
    Optional<String> getLink();
    int wordCount(String word);
}
