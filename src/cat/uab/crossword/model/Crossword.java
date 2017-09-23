package cat.uab.crossword.model;

import cat.uab.crossword.exception.CrosswordFileException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Crossword {

    //Singleton instance
    private static Crossword instance = null;

    private char matrix[][];
    private ArrayList<String> lines;
    private File file;

    /**
     * Getter instance crossword
     * @return Crossword
     */
    public static Crossword getCrossword(){
        return instance;
    }

    /**
     * Load and fill matrix of crossword from file
     * @param file
     * @return Instance of crossword
     */
    public static Crossword loadCrossword(File file){

        if(instance == null)
            instance = new Crossword();

        instance.lines = new ArrayList<>();
        instance.file = file;

        try {
            instance.loadFile();
        } catch (CrosswordFileException e) {
            e.printStackTrace();
        }
        instance.loadMatrix();

        return instance;
    }

    /**
     * Fill matrix with file data
     */
    private void loadMatrix(){

        int i, j;
        int rowCount;
        int colCount;
        String line[];

        rowCount = this.getRowHeight();
        colCount = this.getColWidth();

        this.matrix = new char[rowCount][colCount];

        for(i = 0; i < rowCount; i++){

            line = this.lines.get(i).split("\t");

            for(j = 0; j < colCount; j++){
               this.matrix[i][j] = line[j].toCharArray()[0];
            }
        }
    }

    /**
     * Return the number of columns
     * @return int
     */
    public int getColWidth(){
        return this.lines.get(0).split("\t").length;
    }

    /**
     * Return the number of rows
     * @return int
     */
    public int getRowHeight(){
        return this.lines.size();
    }

    /**
     * Load file data on crossword structure.
     * @throws CrosswordFileException
     */
    private void loadFile() throws CrosswordFileException {

        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader(this.file))) {
            while ((line = reader.readLine()) != null) {
                this.lines.add(line);
            }

        } catch (Exception e) {
            throw new CrosswordFileException(this.file);
        }
    }

}
