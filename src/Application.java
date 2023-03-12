import word_occurrences.Occurrences;

import java.io.FileNotFoundException;

public class Application {

    public static void main(String[] args) {
        try {
            test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void test() throws FileNotFoundException {
        Occurrences occ = new Occurrences(RootPath.rootPath);
        System.out.println(occ);
        System.out.println("distinct words: " + occ.distinctWords());
        System.out.println("total occurrences: " + occ.totalOccurrences());
    }

}
