package cat.uab.crossword.model;

import java.util.TreeSet;

public class Word {

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private String wordAssigned = "";
    private TreeSet<Restriction> restrictions = new TreeSet<Restriction>();
    private int id;
    private int orientation;

    /**
     * Constructor
     */
    public Word(int id, int orientation) {
        this.id = id;
        this.orientation = orientation;
    }

    /**
    *Adds a restriction if already doesn't exist.
    */
    public void AddRestriction(Restriction restToAdd){
        restrictions.add(restToAdd);
    }

    /**
    *Getter
    * @return the word assigned to this word
    */
    public String getWordAssigned() {
        return wordAssigned;
    }

    /**
    * Setter
    * @param: String of word to assign
    */
    public void setWordAssigned(String wordAssigned) { this.wordAssigned = wordAssigned; }

    /**
    * Getter
    * @return the id assigned to this word
    */
    public int getId() {
        return id;
    }

    /**
    *   Getter
    *   @return the orientation of this word
    */
    public int getOrientation() {
        return orientation;
    }

}
