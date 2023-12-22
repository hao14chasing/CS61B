package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PAGALetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PAGALetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN and the GUESSES that have been made. */
    public char getGuess(String pattern, List<Character> guesses) {
        // TODO: Fill in this method.
       /** Map<Character,Integer> map1=new TreeMap<>();*/
 /**match the pattern*/
        List<String>words1=new ArrayList<String>(words);
        for (String word:words){
            if (word.length()==pattern.length()){
                for (int i=0;i<pattern.length();i++){
                    if ((pattern.charAt(i)=='-'&& guesses.contains(word.charAt(i))) ||(pattern.charAt(i)!='-' && pattern.charAt(i)!=word.charAt(i))){
                        words1.remove(word);
                    }
                }
            }
            else{
                words1.remove(word);
            }
        }





        /**
        for (int i=0;i<pattern.length();i++){
            for (char letter:guesses){
                if (pattern.charAt(i)!='-'&& pattern.charAt(i)!=letter){
                    for (String word:words){
                        for (int z=0;z<word.length();z++){
                            if (word.charAt(z)==letter){
                                words1.remove(word);
                            }
                        }

                    }
                }
            }
        }*/



        /**match the exact pattern
        List<String>words2=new ArrayList<String>(words1);

        for (Integer x:map1.values()){
            for (String y:words1){
                for (int i=0;i<y.length();i++){
                    if (i==x){
                        continue;
                    }
                    if (y.charAt(i)==pattern.charAt(x)){
                        words2.remove(y);
                    }
                }
            }
        }*/


        /**get where the letter is in a pattern
        for (int i=0;i<pattern.length();i++) {
            if (pattern.charAt(i) != '-' ) {
                map1.put(pattern.charAt(i), i);
            }
        }
        for (Integer x: map1.values()){
            for (String y:words){
                if (y.charAt(x)!=pattern.charAt(x)){
                    words1.remove(y);
                }
            }
        }*/


/**get words frequency*/
        Map<Character,Integer> map = new TreeMap<>();
        for (String x: words1){
            for (int i=0;i<x.length();i++){
                char elem=x.charAt(i);
                if (map.containsKey(x.charAt(i))){
                    map.put(elem,map.get(elem)+1);
                }
                else{
                    map.put(x.charAt(i),1);
                }
            }
        }


 /**print the next greatest number */
        map.put('?',-1);
        char a='?';
        for(char elem:map.keySet()){
            if((!guesses.contains(elem))&&(map.get(elem)>map.get(a))){
                a=elem;
            }
        }
        return a;
    }

    public static void main(String[] args) {
        PAGALetterFreqGuesser pagalfg = new PAGALetterFreqGuesser("data/example.txt");
        System.out.println(pagalfg.getGuess("----", List.of('e')));
    }
}
