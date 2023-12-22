package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PatternAwareLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PatternAwareLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    public Map<Character, Integer> getFrequencyPattern() {
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


    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN. */
    public char getGuess(String pattern, List<Character> guesses) {
        // TODO: Fill in this method.
        Map<Character,Integer> map1=new TreeMap<>();

        for (int i=0;i<pattern.length();i++) {
            if (pattern.charAt(i) != '-') {
                map1.put(pattern.charAt(i), i);
            }
        }

        List<String>words1=new ArrayList<String>(words);
        for (Integer x: map1.values()){
            for (String y:words){
                if (y.charAt(x)!=pattern.charAt(x)){
                    words1.remove(y);
                }
            }
        }


        Map<Character,Integer> map = new TreeMap<>();
        for (String x: words1){
            for (int i=0;i<x.length();i++){
                if (map.containsKey(x.charAt(i))){
                    map.put(x.charAt(i),map.get(x.charAt(i))+1);
                }
                else{
                    map.put(x.charAt(i),1);
                }
            }
        }

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
        PatternAwareLetterFreqGuesser palfg = new PatternAwareLetterFreqGuesser("data/example.txt");
        System.out.println(palfg.getGuess("-e--", List.of('e')));
    }
}