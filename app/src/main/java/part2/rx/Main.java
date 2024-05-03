package part2.rx;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
//import RequestHandlerJSoup
import org.jsoup.nodes.Element;
import part2.virtualThread.utils.connection.RequestHandlerJSoup;
import part2.virtualThread.utils.parser.Body;
import part2.virtualThread.utils.parser.HtmlParser;


public class Main {
    URI uri = URI.create("https://www.google.com");
    int depth = 2;
    String word = "Google";

    static int count = 0;

    //this class mast search for the word in the uri and all the links in the uri for max depth
    // using recursion and rxjava
    // count the number of times the word is found in the uri and all the links in the uri and print all the links


    public static void main(String[] args) throws Exception {
        //use RequestHandlerJSoup to get html links
        RequestHandlerJSoup requestHandlerJSoup = new RequestHandlerJSoup();
        //get the body of the uri
        Body<Element> body = requestHandlerJSoup.getBody("https://www.google.com");

        HtmlParser.parse(body).foreachWord(word -> {

            if (word.equals("google")) {
                count++;
                System.out.println(count);
            }
        });
    }





}
