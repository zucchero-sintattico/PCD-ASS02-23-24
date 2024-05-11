package part2.virtualThreadFuture.search;

import part2.virtualThreadFuture.state.SearchReport;

import java.util.Optional;

public interface SearchController {

    void start(String address, String word, int depth);

    void stop();

    void bruteStop();

    Optional<SearchReport> getSearchInfo();

}
