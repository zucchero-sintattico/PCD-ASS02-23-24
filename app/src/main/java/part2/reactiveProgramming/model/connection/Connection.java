package part2.reactiveProgramming.model.connection;

import java.io.InputStream;
import java.util.Optional;
import java.util.Scanner;

public interface Connection {
    Optional<InputStream> getConnection(String url);
    Optional<Scanner> getScannerConnection(String url);
}
