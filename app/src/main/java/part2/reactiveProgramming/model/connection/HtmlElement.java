package part2.reactiveProgramming.model.connection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Optional;

public class HtmlElement implements Html{

    private final Document htmlString;

    public HtmlElement(String htmlString) {
        this.htmlString = Jsoup.parse(htmlString);
    }

    @Override
    public String getHtml() {
        return this.htmlString.html();
    }

    @Override
    public String getText() {
        return this.htmlString.text();
    }

    @Override
    public boolean isLink() {
        return this.htmlString.select("a").attr("href").startsWith("https");
    }

    @Override
    public Optional<String> getLink() {
        return (this.isLink()) ? Optional.of(this.htmlString.select("a").attr("href")) : Optional.empty();
    }

    @Override
    public int wordCount(String word) {
        return Long.valueOf(this.getText().lines().map(String::toLowerCase).filter(e -> e.contains(word)).count()).intValue();
    }
}
