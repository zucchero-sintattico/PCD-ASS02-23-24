package part2.rx.model;

import java.util.stream.Stream;

public record SearchReport (String url, int wordFound, int depth, Stream<String> links, int totalWordCount){}
