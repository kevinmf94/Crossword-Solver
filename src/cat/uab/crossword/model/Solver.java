package cat.uab.crossword.model;

import java.util.List;

public class Solver {
    private Crossword crossword;
    private Dictionary dic;

    public Solver(Crossword crossword, Dictionary dic) {
        this.crossword = crossword;
        this.dic = dic;
    }
    public void f(){
        recursive(crossword.getWords());
        System.out.println("REVISAR WORDS");
    }
    private boolean recursive(List<Word> words){
        if(words.isEmpty()) {
            return true;
        }
        Word actWord = words.get(0);
        for (String word: dic.get(actWord.getLength()) ){
            if (actWord.WordIsValid(word)){
                actWord.setWordAssigned(word);
                if(recursive(words.subList(1, words.size()))){
                    return true;
                }
            }
        }
        return false;
    }
}
