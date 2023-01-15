package test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class IOSearcher {

    static boolean search(String word, String... fileNames) {
        //search the word in all the files
        for (String fileName : fileNames) {
            Stream<String> stream;
            try {
                stream = Files.lines(Paths.get(fileName));
                if (stream.filter(line -> line.contains(word)).count() != 0)
                    return true;
                stream.close();
            } catch (IOException e) {
                System.out.println("error");
            }
        }
        return false;
    }
}