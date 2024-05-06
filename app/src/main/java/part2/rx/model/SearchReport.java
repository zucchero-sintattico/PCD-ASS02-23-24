package part2.rx.model;

import java.util.stream.Stream;

public record SearchReport (String url, int wordFind, int depth, Stream<String> links, int totalWordCount){}
