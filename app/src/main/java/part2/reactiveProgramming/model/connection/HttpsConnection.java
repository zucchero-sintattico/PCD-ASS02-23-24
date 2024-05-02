package part2.reactiveProgramming.model.connection;

import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Scanner;

public class HttpsConnection implements Connection{

    @Override
    public Optional<InputStream> getConnection(String url) {
        try{
            InputStream inputStream = new URI(url).toURL().openStream();
            return Optional.of(inputStream);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Scanner> getScannerConnection(String url) {
        Optional<InputStream> inputStream = this.getConnection(url);
        return inputStream.map(stream -> new Scanner(stream, StandardCharsets.UTF_8));
    }
}
