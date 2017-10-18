package cat.uab.crossword.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Solver {

    //AuxiliarVariable for obtain efficiency information
    int openedNodes = 0;

    //Linked list: Add and Remove = O(1). Get O(n) but, the only time that we need to get, we need to looks all list too.
    private LinkedList<Word> noVisitedWords = new LinkedList<Word>();

    /**
    * HashMap : Wants an object by ID = O(1).
    * LinkedList : Add and Remove = O(1). Get O(n) but, the only time that we need to get, we need to looks all list too.
    */
    private HashMap<Integer, LinkedList<String>> consistencyDic = new HashMap<Integer, LinkedList<String>>();

    /** HashMap : Wants an object by ID = O(1).
    *            Stack : Put on top and take on top is O(1), and we need it to emulate the StackPile of the recursive process
    *            LinkedList : Add and Remove = O(1). Get O(n) but, the only time that we need to get, we need to looks all list too.
    *                        In addition, we have addAll, that is O(1) too in this case.
    **/
    private HashMap<Integer, Stack<LinkedList<String>>> StackOfState = new HashMap<Integer, Stack<LinkedList<String>>>();

    //HashMap: ContainsKey, put, and remove is O(1) (O(hash function time))
    private HashMap<String, Boolean> wordsAsssigned = new HashMap<String, Boolean>();

    /**
     * Constructor
     */
    public Solver() {
        loadConsistencyDic();
        noVisitedWords.addAll(Crossword.getCrossword().getWords());
    }

    /**
     * Load consistency dic
     */
    private void loadConsistencyDic(){
        for(Word w: Crossword.getCrossword().getWords()){
            consistencyDic.put(w.getIDInDic(), Dictionary.getDictionary().get(w.getLength()));
            StackOfState.put(w.getIDInDic(), new Stack<LinkedList<String>>());
        }
    }

    /**
    * Applies arc consistency to the domains of the words affected by wordChanged
    */
    private boolean applyArcConsistency(Word wordChanged){
        Queue<LinkedList<String>> newDomains = new LinkedList<LinkedList<String>>();
        Queue<LinkedList<String>> lastStates = new LinkedList<LinkedList<String>>();
        for (Restriction rest : wordChanged.getRestrictions()){
            LinkedList<String> newDomain = new LinkedList<String>();
            LinkedList<String> lastState = new LinkedList<String>();
            for(String word : consistencyDic.get(rest.getOtherWord().getIDInDic())) {
                if (wordChanged.getWordAssigned().toCharArray()[rest.getPosOfMyWord()] == word.toCharArray()[rest.getPosOfOtherWord()]) {
                    newDomain.add(word);
                } else{
                    lastState.add(word);
                }
            }
                if(newDomain.isEmpty())
                    return false;
                newDomains.add(newDomain);
                lastStates.add(lastState);
        }
        for (Restriction rest : wordChanged.getRestrictions()){
            this.consistencyDic.replace(rest.getOtherWord().getIDInDic(),newDomains.poll());
            this.StackOfState.get(rest.getOtherWord().getIDInDic()).push(lastStates.poll());
        }
        return true;
    }

    /**
    * Erase the changes of the last arc consistency applyed
    */
    private void unApplyArcConsistency(Word wordChanged){
        for (Restriction rest : wordChanged.getRestrictions()){
            consistencyDic.get(rest.getOtherWord().getIDInDic()).addAll(StackOfState.get(rest.getOtherWord().getIDInDic()).pop());
        }
    }

    public void resolve(){
        long time_init = System.currentTimeMillis();
        if(backtracking())
        System.out.println("Backtracking done in "+(System.currentTimeMillis()-time_init)/(float)1000+ " Seconds.\n" +
                "Opened nodes: "+openedNodes);
        else {
            System.out.println("No existeix una solució, per aquesta combinació de Crossword i diccionari");
            System.exit(-1);
        }
    }

    public void printResult(){
        for(Word word: Crossword.getCrossword().getWords()){
            System.out.println(word.getId()+" "+word.getOrientation()+" "+word.getWordAssigned());
        }
    }

    private boolean backtracking(){
        //-----The algorithm has ended?-------
        if(this.noVisitedWords.isEmpty()) {
            return true;
        }
        //Select a word and erase it from the posible words
        Word actWord = nextWordToVisit();
        this.noVisitedWords.remove(actWord);
        //For each word in the domain of actWord
        for (String word : consistencyDic.get(actWord.getIDInDic())){
            //Increment in one unit the values tried (the nodes opened).
            openedNodes++;
            //The probability of the word are already in the crossword is so Slowly... is worthit have it that erase from all domains in each time
            if(wordsAsssigned.containsKey(word))
                continue;
            //Is a valid word always, assign it and tracts to view what happens
            actWord.setWordAssigned(word);
            //Apply arc consistency to the affected domains
            if(!applyArcConsistency(actWord)) {
                continue; //if this words erases the domain of any collindant word, this word is not varil
            }
            //Puts the word on dictionary of words used, for avoid the repetition
            wordsAsssigned.put(word, true);
            if(backtracking()){
                //If this node is in the way of a solution founded... Ends the algorithm!
                return true;
            }
            // If not is in the way of the solution, undo the changes done by this word...
            wordsAsssigned.remove(word);
            unApplyArcConsistency(actWord);
        }
        //If arrives here... Not exist any solution in this way... it hurts, but... Another way can be more sweet for you
        actWord.setWordAssigned("");
        this.noVisitedWords.add(actWord);
        return false;
    }

    /*
    * Return the best word visit now. The word that now have the minimum domain.
    */
    private Word nextWordToVisit(){
        Word nextWord = null;
        int minSize = Integer.MAX_VALUE;
        //Don't go wich simple for, because want by index in a LinkedList is O(n){
        for (Word w : noVisitedWords){
            if(consistencyDic.get(w.getIDInDic()).size()<minSize){
                nextWord = w;
                minSize = consistencyDic.get(w.getIDInDic()).size();
            }
        }
        return nextWord;
    }
}
