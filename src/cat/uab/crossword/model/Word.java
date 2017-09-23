package cat.uab.crossword.model;

import java.sql.Types;

public class Word {

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    private int id;
    private int orientation;

    public Word(int id, int orientation) {
        this.id = (id-48);
        this.orientation = orientation;
    }
}
