package cat.uab.crossword.model;

import java.sql.Types;
import java.util.ArrayList;
import java.util.TreeSet;

public class Word {

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private String wordAssigned = "";
    private TreeSet<Restriction> restrictions = new TreeSet<Restriction>();
    private int id;
    private int orientation;
    private int length;
    /**
     * Constructor
     */
    public Word(int id, int orientation) {
        this.id = (id);
        this.orientation = orientation;
    }
    /*
    *Adds a restriction if already doesn't exist.
     */
    public void AddRestriction(Restriction restToAdd){
        restrictions.add(restToAdd);
    }

    public boolean WordIsValid(String word){
        for(Restriction rest : restrictions){
            if (!rest.CheckRestriction(word))
                return false;
        }
        return true;
    }
    /*
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

    public void setLength(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    /*
                *Getter
                * @return the id assigned to this word
                 */
    public int getId() {
        return id;
    }

    /*
        *Getter
        * @return the orientation of this word
         */
    public int getOrientation() {
        return orientation;
    }

}
