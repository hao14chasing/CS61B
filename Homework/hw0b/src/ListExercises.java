import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ListExercises {

    /** Returns the total sum in a list of integers */
	public static int sum(List<Integer> L) {
        if (L.size()==0)
        return 0;
        else {
            int z=0;
            for (int elem: L){
                z+=elem;
            }
            return z;
        }

    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        List<Integer> lst = new ArrayList<>();
        for (int num:L){
            if (num%2 ==0){
                lst.add(num);
            }
            else{
                lst=lst;
            }
        }
        return lst;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        List<Integer> lst = new ArrayList<>();
        for (int num:L1){
            for (int trac:L2){
                if (num==trac){
                    lst.add(num);
                }
                else{
                    lst=lst;
                }
            }
        }
        return lst;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        int z=0;
        for (String num: words){
            for (int i=0; i<num.length();i++){
                if (c==num.charAt(i)){
                    z++;
                }
            }
        }
        return z;
    }
}
