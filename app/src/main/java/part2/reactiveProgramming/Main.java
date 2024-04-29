package part2.reactiveProgramming;

import org.jsoup.Jsoup;
import part2.reactiveProgramming.model.ConcurrentReader;

public class Main {
    public static void main(String[] args) {
        String url = "https://en.wikipedia.org/";
        ConcurrentReader reader = new ConcurrentReader();
        var result =
                reader.getAllLines(url)
                        .filter(e -> e.contains("wikipedia"))
                        .map(e -> "[Lines] : " + e);
        result.subscribe(i -> System.out.println("[Lines]: " + i));
        result.subscribe(i -> {
            var lines = Jsoup.parse(i).select("a").attr("href");
            if(lines.startsWith("https://")){
                System.out.println("[Link]: " + lines);
            }
        });
    }
}
