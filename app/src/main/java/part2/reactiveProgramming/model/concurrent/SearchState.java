package part2.reactiveProgramming.model.concurrent;

import io.reactivex.rxjava3.core.Flowable;

public record SearchState(Flowable<String> url, String word, int depth){}
