package part2.reactiveProgramming.model;

import java.util.Set;

public interface ReportListener {
    void wordFounded(String currentUrl, Set<String> link, Long word);
}
