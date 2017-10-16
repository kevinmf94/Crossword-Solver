/*
* Authors of this practice:
* Eric Canas (n is an enye really)
* Kevin Martin
* Bernat Martinez
 */

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
        Solver solver = new Solver();
        solver.resolve();
        //Print results.
        char auxPrintMatrix [][] = Crossword.getCrossword().getStateOfCrossword();
        System.out.println("\n ------------CROSSWORD------------ \n");
        for (int i=0;i<auxPrintMatrix.length;i++) {
            for (int j = 0; j < auxPrintMatrix[0].length; j++) {
                System.out.print("|");
                System.out.print(auxPrintMatrix[i][j]);
            }
            System.out.println("|");
        }
        System.out.println("\n -------VALUES OF VARIABLES-------- \n");
        solver.printResult();

    }
}
