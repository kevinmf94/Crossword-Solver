package cat.uab.crossword;

import cat.uab.crossword.model.Crossword;
import cat.uab.crossword.model.Dictionary;

import java.io.File;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Main {

    public static final String FILES_DIR = "files/";
    public static final String CROSSWORD_FILE = "crossword_A.txt";
    public static final String DICTIONARY_FILE = "diccionari_A.txt";

    public static void main (String [ ] args) {

        long timeStart;
        long timeEnd;

        //Load Dictionary
        timeStart = System.currentTimeMillis();
        File file = new File(FILES_DIR+DICTIONARY_FILE);
        Dictionary dic = Dictionary.loadDictionary(file);

        timeEnd = System.currentTimeMillis();
        System.out.println("TIME Load dictionary: "+(timeEnd-timeStart));

        //Load Crossword
        timeStart = System.currentTimeMillis();
        File file2 = new File(FILES_DIR+CROSSWORD_FILE);
        Crossword cross = Crossword.loadCrossword(file2);

        timeEnd = System.currentTimeMillis();
        System.out.println("TIME Load Crossword: "+(timeEnd-timeStart));

        //Ejemplo de filtro por letra y posicion + PRUEBA DE TIME
        timeStart = System.currentTimeMillis();

        List<String> beerDrinkers = dic.get(10).stream().filter(line -> line.toCharArray()[1] == 'N').collect(Collectors.toList());
        TreeSet<String> aa = new TreeSet<>(beerDrinkers);

        timeEnd = System.currentTimeMillis();
        System.out.println("TIME Apply Dictionary Filter : "+(timeEnd-timeStart));
    }
}
