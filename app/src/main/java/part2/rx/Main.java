package part2.rx;

import part2.rx.view.GUI;
import part2.utils.parser.HtmlParser;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    private static final String excludedExtensionPath = "app/src/main/java/part2/resources/file/ExtensionToExclude.txt";

    public static void main(String[] args){
        try {
            List<String> lines = Files.readAllLines(Paths.get(excludedExtensionPath));
            HtmlParser.addExtensionToFilter(lines.toArray(String[]::new));
        } catch (IOException e) {
            System.out.println("Failed to load extension to exclude file");
        }
        SwingUtilities.invokeLater(() -> {
            new GUI().display();
        });
    }
}
