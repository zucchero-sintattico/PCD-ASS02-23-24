package part2.rx;

import java.util.stream.Stream;

public record SearchReport (String url, int wordFind, Stream<String> links){}
