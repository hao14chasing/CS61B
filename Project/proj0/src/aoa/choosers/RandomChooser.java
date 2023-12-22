package aoa.choosers;

import edu.princeton.cs.algs4.StdRandom;
import aoa.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class RandomChooser implements Chooser {
    private final String chosenWord;
    private String pattern="-";

    public RandomChooser(int wordLength, String dictionaryFile) {
        // TODO: Fill in/change this constructor.
        List<String> words;
        words=FileUtils.readWords(dictionaryFile);

        if (wordLength<1){
            throw new IllegalArgumentException();
        }

        String check="false";
        List<String>words1=new ArrayList<String>();
        for(String word:words){
            if (word.length()==wordLength){
                check="true";
                words1.add(word);
            }
        }

        if (check=="false"){
            throw new IllegalStateException();
        }



        int numWords=words1.size();
        int randomlyChosenWordNumber=StdRandom.uniform(numWords);
        chosenWord = words1.get(randomlyChosenWordNumber);

        for (int i=0;i<chosenWord.length()-1;i++) {
            pattern += "-";
        }



    }

    @Override
    public int makeGuess(char letter) {
        // TODO: Fill in this method.
        int track=0;
        for (int i=0;i<chosenWord.length();i++){
            if(chosenWord.charAt(i)==letter){
                track+=1;
            }
        }
        //update pattern
        /**for (int i=0;i<chosenWord.length()-1;i++) {
            pattern += "-";
        }*/
        if (track!=0){
            {for (int i=0;i<chosenWord.length();i++){
                if(chosenWord.charAt(i)==letter){
                    pattern=pattern.substring(0,i)+letter+pattern.substring(i+1);
                }
            }

            }
        }


        return track;
    }


    @Override
    public String getPattern() {
        // TODO: Fill in this method.
        return pattern;
    }

    @Override
    public String getWord() {
        // TODO: Fill in this method.
        return chosenWord;
    }
}
