package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.*;

public class NaiveLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public NaiveLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Makes a guess which ignores the given pattern. */
    public char getGuess(String pattern, List<Character> guesses) {
        return getGuess(guesses);
    }

    /** Returns a map from a given letter to its frequency across all words.
     *  This task is similar to something you did in hw0b! */
    public Map<Character, Integer> getFrequencyMap() {
        // TODO: Fill in this method.
        Map<Character,Integer> map = new TreeMap<>();
        for (String x: words){
            for (int i=0;i<x.length();i++){
                if (map.containsKey(x.charAt(i))){
                    map.put(x.charAt(i),map.get(x.charAt(i))+1);
                }
                else{
                    map.put(x.charAt(i),1);
                }
            }
        }
        return map;
    }

    /** Returns the most common letter in WORDS that has not yet been guessed
     *  (and therefore isn't present in GUESSES). */
    public char getGuess(List<Character> guesses) {
        // TODO: Fill in this method.
        Map<Character,Integer> map=this.getFrequencyMap();
        if (map==null){
            return '?';
        }
        else {
            for (Character x : guesses) {
                map.remove(x);
            }
        }
        int max=0;
        for (int y:map.values()){
            if (y<max){
                max=max;
            }
            else {
                max=y;
            }
        }
        char a='a';
       for (Character z:map.keySet()){
           if (map.get(z)==max){
               a=z;
               break;
           }
       }
       return a;
    }

    public static void main(String[] args) {
        NaiveLetterFreqGuesser nlfg = new NaiveLetterFreqGuesser("data/example.txt");
        System.out.println("list of words: " + nlfg.words);
        System.out.println("frequency map: " + nlfg.getFrequencyMap());

        List<Character> guesses = List.of('e', 'l');
        System.out.println("guess: " + nlfg.getGuess(guesses));
    }
}
