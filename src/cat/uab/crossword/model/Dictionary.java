package cat.uab.crossword.model;

import cat.uab.crossword.exception.DictionaryFileException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.TreeSet;

public class Dictionary extends HashMap<Integer, TreeSet<String>> {

    private static Dictionary instance = null;

    private File file;

    private Dictionary() {}

    public static Dictionary getDictionary(){
        return instance;
    }

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

    private void deleteDictionary(){
        for (Integer key: this.keySet()) {
            this.get(key).clear();
        }

        this.clear();
    }

    private void loadFile() throws DictionaryFileException {

        String line;
        long timeStart;
        long timeEnd;

        try (BufferedReader reader = new BufferedReader(new FileReader(this.file))) {
            timeStart = System.currentTimeMillis();

            while ((line = reader.readLine()) != null) {
                if(this.get(line.length()) == null)
                    this.put(line.length(), new TreeSet<>());

                this.get(line.length()).add(line);
            }
            timeEnd = System.currentTimeMillis();
            System.out.println("TIME: "+(timeEnd-timeStart));

        } catch (Exception e) {
            throw new DictionaryFileException(this.file);
        }
    }
}
