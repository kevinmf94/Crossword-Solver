package cat.uab.crossword.model;

import java.util.TreeSet;

public class Word {

    public static final int HORIZONTAL = 200;
    public static final int VERTICAL = 100;
    private String wordAssigned = "";
    private TreeSet<Restriction> restrictions = new TreeSet<>();
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

    /**
    * Adds a restriction if already doesn't exist.
    */
    public void AddRestriction(Restriction restToAdd){
        restrictions.add(restToAdd);
    }

    @Deprecated
    public boolean WordIsValid(String word){
        for(Restriction rest : restrictions){
            if (!rest.checkRestriction(word))
                return false;
        }
        return true;
    }

    /**
     * Return id in dictionary
     * @return int
     */
    public int getIDInDic(){
        return orientation+id;
    }
    public TreeSet<Restriction> getRestrictions() {
        return restrictions;
    }

    /**
    * Getter of word assigned
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

    /**
    * Getter
    * @return the id assigned to this word
    */
    public int getId() {
        return id;
    }

    /**
    * Getter
    * @return the orientation of this word
    */
    public int getOrientation() {
        return orientation;
    }

}
