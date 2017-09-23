package cat.uab.crossword.model;

import cat.uab.crossword.exception.DictionaryFileException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.TreeSet;

public class Dictionary extends HashMap<Integer, TreeSet<String>> {

    //Singleton instance
    private static Dictionary instance = null;

    private File file;

    /**
     * Constructor
     */
    private Dictionary() {}

    /**
     * Getter
     * @return Instance of dictionary
     */
    public static Dictionary getDictionary(){
        return instance;
    }

    /**
     * Load and create dictionary from file
     * @param file
     * @return Instance of dictionary
     */
    public static Dictionary loadDictionary(File file){

        if(instance == null) {
            instance = new Dictionary();
        } else
            instance.deleteDictionary();

        instance.file = file;

        try {
            instance.loadFile();
        } catch (DictionaryFileException e) {
            e.printStackTrace();
        }

        return instance;
    }

    /**
     * Clear actual dictionary
     */
    private void deleteDictionary(){
        for (Integer key: this.keySet()) {
            this.get(key).clear();
        }

        this.clear();
    }

    /**
     * Load file data on dictionary structure.
     * @throws DictionaryFileException
     */
    private void loadFile() throws DictionaryFileException {

        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader(this.file))) {

            while ((line = reader.readLine()) != null) {
                if(this.get(line.length()) == null)
                    this.put(line.length(), new TreeSet<>());

                this.get(line.length()).add(line);
            }

        } catch (Exception e) {
            throw new DictionaryFileException(this.file);
        }
    }
}
