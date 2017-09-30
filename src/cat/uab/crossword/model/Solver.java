package cat.uab.crossword.model;

import java.util.*;
import java.util.stream.Collectors;

public class Solver {

    private Dictionary dic;
    private List<Word> result;
    private List<Word> words;
    private LinkedList<Word> noVisitedWords = new LinkedList<Word>();
    private HashMap<Integer, TreeSet<String>> consistencyDic = new HashMap<Integer, TreeSet<String>>();
    private HashMap<Integer, Stack<TreeSet<String>>> StackOfState = new HashMap<Integer, Stack<TreeSet<String>>>();
    private HashMap<String, Boolean> wordsAsssigned = new HashMap<String, Boolean>();
    public Solver(Dictionary dic) {
        this.dic = dic;
        this.result = Crossword.getCrossword().getWords();
        this.words = Crossword.getCrossword().getWords();
        loadConsistencyDic();
        noVisitedWords.addAll(words);
    }

    private void loadConsistencyDic(){
        for(Word w: words){
            consistencyDic.put(w.getIDInDic(), dic.get(w.getLength()));
            StackOfState.put(w.getIDInDic(), new Stack<TreeSet<String>>());
        }
    }

    private boolean applyArcConsistency(Word wordChanged){
        //Mejorables usando colas
        ArrayList<TreeSet<String>> newDomains = new ArrayList<TreeSet<String>>();
        ArrayList<TreeSet<String>> lastStates = new ArrayList<TreeSet<String>>();
        for (Restriction rest : wordChanged.getRestrictions()){
            TreeSet<String> newDomain = new TreeSet<String>();
            TreeSet<String> lastState = new TreeSet<String>();
            for(String word : consistencyDic.get(rest.getOtherWord().getIDInDic())) {
                if (wordChanged.getWordAssigned().toCharArray()[rest.getPosOfMyWord()] == word.toCharArray()[rest.getPosOfOtherWord()]) {
                    newDomain.add(word);
                } else{
                    lastState.add(word);
                }
            }
                if(newDomain.isEmpty())
                    return false;
                newDomains.add(0, newDomain);
                lastStates.add(0, lastState);
        }
        int i=1;
        for (Restriction rest : wordChanged.getRestrictions()){
            this.consistencyDic.replace(rest.getOtherWord().getIDInDic(),newDomains.get(newDomains.size()-i));
            this.StackOfState.get(rest.getOtherWord().getIDInDic()).push(lastStates.get(lastStates.size()-i));
            i++;
        }
        return true;
    }

    private void unApplyArcConsistency(Word wordChanged){
        for (Restriction rest : wordChanged.getRestrictions()){
            consistencyDic.get(rest.getOtherWord().getIDInDic()).addAll(StackOfState.get(rest.getOtherWord().getIDInDic()).pop());
        }
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
                    return 1;
                else if (o1.getRestrictions().size() > o2.getRestrictions().size())
                    return -1;
                else
                    return 0;
            }
        });*/
        long time_init = System.nanoTime();
        recursive();
        System.out.println(System.nanoTime()-time_init);
    }

    public void printResult(){
        for(Word word: this.result){
            System.out.println(word.getId()+" "+word.getOrientation()+" "+word.getWordAssigned());
        }
    }

    private boolean recursive(){

        if(this.noVisitedWords.isEmpty()) {
            return true;
        }

        Word actWord = nextWordToVisit();
        this.noVisitedWords.remove(actWord);
        //List<String> domain = this.getDomain(actWord, dic.get(actWord.getLength()));
        for (String word : consistencyDic.get(actWord.getIDInDic())){
            //The probability is so Slowly... is worthit have it that erase from all domains in each time
            if(wordsAsssigned.containsKey(word))
                continue;
            actWord.setWordAssigned(word);
            if(!applyArcConsistency(actWord)) {
                continue;
            }
            wordsAsssigned.put(word, true);
            //System.out.println("------------------------");
            //printResult();
            if(recursive()){
                return true; //If this ends in a correct solution
            }
            wordsAsssigned.remove(word);
            unApplyArcConsistency(actWord);
        }
        actWord.setWordAssigned("");
        this.noVisitedWords.add(actWord);
        return false;
    }
    private Word nextWordToVisit(){
        Word possibleNextWord = null;
        int minSize = Integer.MAX_VALUE;
        //Don't go wich simple for, because want in list have "n" order{
        for (Word w : noVisitedWords){
            if(consistencyDic.get(w.getIDInDic()).size()<minSize){
                possibleNextWord = w;
                minSize = consistencyDic.get(w.getIDInDic()).size();
            }
        }
        return possibleNextWord;
    }

    private TreeSet<String> getDomain(Word word, TreeSet<String> dic){

        TreeSet<String> valid = new TreeSet<>();

        for (String wordDic : dic){
            if(word.WordIsValid(wordDic))
                valid.add(wordDic);
        }
        return valid;


        //return this.dic.get(word.getLength()).stream().filter(line -> word.WordIsValid(line)).collect(Collectors.toList());
    }
}
