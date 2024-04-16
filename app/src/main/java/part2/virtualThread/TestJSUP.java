package part2.virtualThread;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class TestJSUP {
    public static void main(String[] args){
        Document doc = null;
        try {
            doc = Jsoup.connect("https://it.wikipedia.org/wiki/Felis_silvestris_catus").get();

            Elements links = doc.select("a[href]");
            String occ = "gatta";
            int count = 0;



//            for (Element link : links) {
//                String l = link.attr("abs:href");
//                System.out.println(l);
//                try {
//                    Document doc22 = Jsoup.connect(l).get();
//                    String allText = doc22.text();
//
//                    int t = StringUtils.countMatches(allText.toLowerCase(), occ);
//                    count += t;
//                    System.out.println(t);
//                } catch (IOException e) {
//                    System.out.println(l+" UNAVAILABLE");
//                }


    //            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
//            }





            Element body = doc.body();
            String allText = body.text();
    //        String occ = "gatta";
            System.out.println(allText);
            int t = StringUtils.countMatches(allText.toLowerCase(), occ);
            count += t;
            System.out.println(t);
            System.out.println(count);

        } catch (IOException e) {

        }

    }
    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }
    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
}
