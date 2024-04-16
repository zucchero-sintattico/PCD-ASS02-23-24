package part2.virtualThread;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    public Test() throws IOException, URISyntaxException {
        URL url = new URI("https://it.wikipedia.org/wiki/Felis_silvestris_catus").toURL();
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        List<String> href = new ArrayList<>();
        String occ = "gatto";
        int count = 0;

        String val;
        while ((val = br.readLine()) != null)   // condition
        {
            System.out.println(val);
            count += StringUtils.countMatches(val.toLowerCase(), occ);
            // execution if condition is true
        }
        System.out.println(count);
    }
}
