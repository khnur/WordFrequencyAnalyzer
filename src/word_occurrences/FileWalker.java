package word_occurrences;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;


final class FileWalker {

    private final Occurrences occ;
    private final File rootDir;

    FileWalker(String rootDirPath, Occurrences occ) throws FileNotFoundException {
        this.occ = occ;
        this.rootDir = new File(rootDirPath);

        if (!this.rootDir.isDirectory()) {
            throw new FileNotFoundException(rootDirPath + " does not exist, " +
                    "or is not a directory.");
        }
    }

    void populateOccurrenceMap() {
        try {
            populateOccurrenceMap(rootDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateOccurrenceMap(File fileOrDir) throws IOException {
        if (fileOrDir.isFile()) {
            BufferedReader reader = new BufferedReader(new FileReader(fileOrDir));
            int row = 1, column = 1, ch;
            String word = "";
            do {
                ch = reader.read();
                if (Syntax.isInWord((char) ch)) {
                    word += (char) ch;
                } else {
                    if (word.length() > 0) occ.put(word, fileOrDir.getPath(), new FilePosition(row, column));
                    column += word.length() + 1;
                    word = "";
                }
                if (Syntax.isNewLine((char) ch)) {
                    row++;
                    column = 1;
                }
            } while (ch != -1);
            reader.close();
        }
        if (fileOrDir.isDirectory()) {
            int num = 0;
            if (fileOrDir.exists()) num = fileOrDir.listFiles().length;
            for (int i = 0; i < num; i++) {
                populateOccurrenceMap(fileOrDir.listFiles()[i]);
            }
        }
    }
}