package cat.uab.crossword.model;

public class Restriction implements Comparable<Restriction>{

    private int posOfMyWord;
    private Word otherWord;
    private int posOfOtherWord;

    /**
     * Constructor
     * @param posOfMyWord: the pos of the character in the current word that contains this restriction
     * @param otherWord: the other word that affects to this restriction
     * @param posOfOtherWord: the pos of the character in the other word that produces the restriction
     */
    public Restriction(int posOfMyWord, Word otherWord, int posOfOtherWord) {
        this.posOfMyWord = posOfMyWord;
        this.otherWord = otherWord;
        this.posOfOtherWord = posOfOtherWord;
    }

    /**
     *
     * @param myWord: the current word
     * @return true if the word cheks the restriction
     */
    public boolean checkRestriction(String myWord){
        return (otherWord.getWordAssigned() == "") || (myWord.toCharArray()[posOfMyWord] == otherWord.getWordAssigned().toCharArray()[posOfOtherWord]);
    }

    public int getPosOfMyWord() {
        return posOfMyWord;
    }

    public Word getOtherWord() {
        return otherWord;
    }

    public int getPosOfOtherWord() {
        return posOfOtherWord;
    }

    @Override
    public int compareTo(Restriction o) {
        if ((this.posOfOtherWord == o.posOfOtherWord) && (this.otherWord.getId() == o.otherWord.getId())
                && (this.otherWord.getOrientation() == o.otherWord.getOrientation()) && (this.posOfMyWord == o.posOfMyWord))
            return 0;
        else
            return -1;
    }
}
