package cat.uab.crossword.test;

import cat.uab.crossword.model.Crossword;
import cat.uab.crossword.model.Dictionary;
import cat.uab.crossword.model.Solver;

import java.io.File;

class SolverTest {

    public static final String FILES_DIR = "files/";
    public static final String CROSSWORD_FILE = "crossword_A.txt";
    public static final String DICTIONARY_FILE = "diccionari_A.txt";

    private Dictionary dic;
    private Solver s;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        //Load Dictionary
        File dicFile = new File(FILES_DIR+DICTIONARY_FILE);
        dic = Dictionary.loadDictionary(dicFile);

        //Load Crossword
        File crossFile = new File(FILES_DIR+CROSSWORD_FILE);
        Crossword cross = Crossword.loadCrossword(crossFile);

    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        s.printResult();
    }

    @org.junit.jupiter.api.Test
    void resolve() {
        s = new Solver(dic);
        s.resolve();
    }

}