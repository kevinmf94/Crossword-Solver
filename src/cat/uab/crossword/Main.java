package cat.uab.crossword;

import cat.uab.crossword.model.Dictionary;

import java.io.File;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Main {

    public static final String FILES_DIR = "files/";

    public static void main (String [ ] args) {

        File file = new File(FILES_DIR+"diccionari_CB.txt");
        Dictionary dic = Dictionary.loadDictionary(file);

        //Ejemplo de filtro por letra y posicion + PRUEBA DE TIME
        long timeStart;
        long timeEnd;
        timeStart = System.currentTimeMillis();

        List<String> beerDrinkers = dic.get(10).stream().filter(line -> line.toCharArray()[1] == 'N').collect(Collectors.toList());
        TreeSet<String> aa = new TreeSet<>(beerDrinkers);

        timeEnd = System.currentTimeMillis();
        System.out.println("TIME: "+(timeEnd-timeStart));


    }
}
