package word_occurrences;

import java.io.FileNotFoundException;
import java.util.TreeMap;
import java.util.TreeSet;

public class Occurrences {

    // Maps words -> filename -> sets of their Positions in the file.
    private final TreeMap<String, TreeMap<String, TreeSet<FilePosition>>> occMap;

    public Occurrences(String rootDirPath) throws FileNotFoundException {
        occMap = new TreeMap<>();
        FileWalker walker = new FileWalker(rootDirPath, this);
        walker.populateOccurrenceMap();
    }

    void put(String word, String filePath, FilePosition pos) {
        word = word.toLowerCase();
        if (!occMap.containsKey(word)) occMap.put(word, new TreeMap<>());
        if (!occMap.get(word).containsKey(filePath)) occMap.get(word).put(filePath, new TreeSet<>());
        occMap.get(word).get(filePath).add(pos);
    }

    /**
     * @return the number of distinct words found in the files
     */
    public int distinctWords() {
        return occMap.size();
    }

    /**
     * @return the number of total word occurrences across all files
     */
    public int totalOccurrences() {
        int num = 0;
        for (TreeMap<String, TreeSet<FilePosition>> eachWord : occMap.values()) {
            for (TreeSet<FilePosition> positionOfWord : eachWord.values()) {
                num += positionOfWord.size();
            }
        }
        return num;
    }

    /**
     * Finds the total number of occurrences of a given word across
     * all files.  If the word is not found among the occurrences,
     * simply return 0.
     *
     * @param word whose occurrences we are counting
     * @return the number of occurrences
     */
    public int totalOccurrencesOfWord(String word) {
        if (!occMap.containsKey(word.toLowerCase())) return 0;
        int num = 0;
        for (TreeSet<FilePosition> posOfWords : occMap.get(word.toLowerCase()).values()) num += posOfWords.size();
        return num;
    }

    /**
     * Finds the total number of occurrences of a given word in the given file.
     * If the file is not found in Occurrences, or if the word does not occur
     * in the file, simply return 0.
     *
     * @param word     whose occurrences we are counting
     * @param filepath of the file
     * @return the number of occurrences
     */
    public int totalOccurrencesOfWordInFile(String word, String filepath) {
        if (!occMap.containsKey(word.toLowerCase())) return 0;
        if (!occMap.get(word.toLowerCase()).containsKey(filepath)) return 0;
        return occMap.get(word.toLowerCase()).get(filepath).size();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        String word = "";
        for (String str : occMap.keySet()) {
            word = "\"" + str + "\" has " + totalOccurrencesOfWord(str) + " occurrence(s):\n";
            sb.append(word);
            for (String dir : occMap.get(str).keySet()) {
                for (FilePosition filePos : occMap.get(str).get(dir)) {
                    word = "   (file: \"" + dir + "\"; " + filePos.toString() + ")\n";
                    sb.append(word);
                }
            }
        }
        return sb.toString();
    }
}


