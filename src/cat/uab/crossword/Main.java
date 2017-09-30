package cat.uab.crossword;

import cat.uab.crossword.model.Crossword;
import cat.uab.crossword.model.Dictionary;
import cat.uab.crossword.model.Solver;
import java.io.File;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Main {

    public static final String FILES_DIR = "files/";
    public static final String CROSSWORD_FILE = "crossword_A.txt";
    public static final String DICTIONARY_FILE = "diccionari_A.txt";

    public static void main (String [ ] args) {

        //Load Dictionary
        File dicFile = new File(FILES_DIR+DICTIONARY_FILE);
        Dictionary dic = Dictionary.loadDictionary(dicFile);

        //Load Crossword
        File crossFile = new File(FILES_DIR+CROSSWORD_FILE);
        Crossword cross = Crossword.loadCrossword(crossFile);

        //Ejemplo de filtro por letra y posicion
        int posicion = 1;
        int lengthWord = 10;
        char charToFilter = 'N';
        //List<String> beerDrinkers = dic.get(lengthWord).stream().filter(line -> line.toCharArray()[posicion] == charToFilter).collect(Collectors.toList());
        //TreeSet<String> aa = new TreeSet<>(beerDrinkers);
        Solver s = new Solver(dic);
        s.resolve();
        List<String> beerDrinkers = dic.get(cross.getWords().get(0).getLength()).stream().filter(line -> cross.getWords().get(0).WordIsValid(line)/*line.toCharArray()[posicion] == charToFilter*/).collect(Collectors.toList());
        TreeSet<String> aa = new TreeSet<>(beerDrinkers);
        System.out.println(aa);
    }
}
