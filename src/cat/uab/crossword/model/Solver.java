package cat.uab.crossword.model;

import java.util.*;

public class Solver {

    private Dictionary dic;
    private List<Word> result;
    private List<Word> words;

    public Solver(Dictionary dic) {
        this.dic = dic;
        this.result = Crossword.getCrossword().getWords();
        this.words = Crossword.getCrossword().getWords();
    }

    public void resolve(){
        /*Collections.sort(this.words, new Comparator<Word>() {
            @Override
            public int compare(Word o1, Word o2) {
                if (o1.getLength() > o2.getLength())
                    return -1;
                else if (o1.getLength() < o2.getLength())
                    return 1;
                else
                    return 0;
            }
        });*/
        /*Collections.sort(this.words, new Comparator<Word>() {
            @Override
            public int compare(Word o1, Word o2) {
                if (o1.getRestrictions().size() < o2.getRestrictions().size())
                    return -1;
                else if (o1.getRestrictions().size() > o2.getRestrictions().size())
                    return 1;
                else
                    return 0;
            }
        });*/
        recursive(words);
    }

    public void printResult(){
        for(Word word: this.result){
            System.out.println(word.getId()+" "+word.getOrientation()+" "+word.getWordAssigned());
        }
    }

    private boolean recursive(List<Word> words){

        if(words.isEmpty()) {
            return true;
        }

        Word actWord = words.get(0);

        List<String> domain = this.getDomain(actWord, dic.get(actWord.getLength()));
        for (String word : domain){
            actWord.setWordAssigned(word);
            if(recursive(words.subList(1, words.size()))){

                return true;
            }
        }
        return false;

    }

    private List<String> getDomain(Word word, TreeSet<String> dic){

        List<String> valid = new ArrayList<>();

        for (String wordDic : dic){
            if(word.WordIsValid(wordDic))
                valid.add(wordDic);
        }

        return valid;
    }
}
