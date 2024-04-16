package part2.virtualThread;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.stream.Stream;

public class Test {
    public Test() throws IOException, URISyntaxException {
        URL url = new URI("https://www.unipg.it").toURL();
        //set user agent
        System.setProperty("http.agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 14_4_1) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.4.1 Safari/605.1.15");
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        Set<String> href = new HashSet<>();
        String occ = "ingegneria";
        int count = 0;

        String val;
        while ((val = br.readLine()) != null)   // condition
        {
            //System.out.println(val);
            String[] split = val.toLowerCase().split(" ");
//            System.out.println(val);
            Stream.of(split).forEach(s ->{
                if(s.trim().contains("href=\"https://")){
                    System.out.println(s);
                    href.add(s.split("\"")[1]);
                }
            });
            count += (int) Stream.of(val.toLowerCase().split(" ")).filter(s ->{
//                        System.out.println(s);
                        return s.trim().equals(occ);
            }).count();

//            count += StringUtils.countMatches(val.toLowerCase(), " "+occ+" ");
            // execution if condition is true
        }
        System.out.println(href.size());
        for (String s : href) {
            try{
                URL url2 = new URI(s).toURL();
                BufferedReader br2 = new BufferedReader(new InputStreamReader(url2.openStream()));
                String val2;
                while ((val2 = br2.readLine()) != null)   // condition
                {
                    count += (int) Stream.of(val2.toLowerCase().split(" ")).filter(s2 -> {
//                        System.out.println(s);
                        return s2.trim().equals(occ);
                    }).count();
                }
            }catch (IOException e){
                System.out.println(s+" UNAVAILABLE");
            }
            System.out.println(s);
        }
        System.out.println(count);
    }
}
