package cat.uab.crossword.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Solver {
    private Dictionary dic;

    public Solver(Dictionary dic) {
        this.dic = dic;
    }
    public void f(){
        List<Word> words = Crossword.getCrossword().getWords();
        Collections.sort(words, new Comparator<Word>() {
            @Override
            public int compare(Word o1, Word o2) {
                if (o1.getRestrictions().size() > o2.getRestrictions().size())
                    return -1;
                else if (o1.getRestrictions().size() < o2.getRestrictions().size())
                    return 1;
                else
                    return 0;
            }
        });
        recursive(words);
        System.out.println("REVISAR WORDS");
    }
    private boolean recursive(List<Word> words){
        if(words.isEmpty()) {
            return true;
        }
        for (int i = 0; i < words.size();i++){

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
