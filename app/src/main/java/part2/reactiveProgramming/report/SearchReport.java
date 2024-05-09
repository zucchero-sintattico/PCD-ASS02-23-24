package part2.reactiveProgramming.report;

import java.util.stream.Stream;

public record SearchReport (String url, int wordFound, int depth, Stream<String> links, int totalWordCount){}
