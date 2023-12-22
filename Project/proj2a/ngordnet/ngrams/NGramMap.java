package ngordnet.ngrams;

import edu.princeton.cs.algs4.In;

import java.util.*;


import static java.lang.Double.parseDouble;
import static org.apache.pdfbox.pdmodel.common.function.type4.InstructionSequenceBuilder.parseInt;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    private static final int MIN_YEAR = 1400;
    private static final int MAX_YEAR = 2100;
    HashMap<String, TimeSeries> wordsMap = new HashMap<String, TimeSeries>();
    TimeSeries countsMap = new TimeSeries();
    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {

        In words = new In(wordsFilename);
        String next;
        String[] tokens;
        int year;
        Double count;
        TimeSeries track = new TimeSeries();
        while (words.hasNextLine() && !words.isEmpty()) {
            next = words.readLine();
            tokens = next.split("[\t]+");
            year = parseInt(tokens[1]);
            count = parseDouble(tokens[2]);
            if (wordsMap.containsKey(tokens[0])) {
                wordsMap.get(tokens[0]).put(year, count);
            } else {
                track.clear();
                track.put(year, count);
                wordsMap.put(tokens[0], new TimeSeries(track));
            }
        }
        In counts = new In(countsFilename);
        while (counts.hasNextLine() && !counts.isEmpty()) {
            next = counts.readLine();
            tokens = next.split("[,]+");
            year = parseInt(tokens[0]);
            count = parseDouble(tokens[1]);
            countsMap.put(year, count);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {

        TimeSeries returnMap = new TimeSeries();
        for (int i = startYear; i <= endYear; i++) {
            if (checkyear(word, i) != 0) {
                returnMap.put(i, checkyear(word, i));
            }
        }
        return returnMap;
    }




    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy,
     * not a link to this NGramMap's TimeSeries. In other words, changes made
     * to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word) {

        return wordsMap.get(word);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {

        return countsMap;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {

        return countHistory(word, startYear, endYear).dividedBy(countsMap);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to
     * all words recorded in that year. If the word is not in the data files, return an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {

        return countHistory(word).dividedBy(countsMap);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS
     * between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     * this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {

        return new TimeSeries(summedWeightHistory(words), startYear, endYear);
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {

        TimeSeries returnValue = new TimeSeries();
        for (String word: words) {
            if (countHistory(word) != null) {
                returnValue = returnValue.plus(weightHistory(word));
            }
        }
        return returnValue;
    }


    private double checkyear(String word, int year) {
        if (wordsMap.get(word) == null || wordsMap.get(word).get(year) == null) {
            return 0;
        }
        return wordsMap.get(word).get(year);
    }

    public List<String> totalWords(int startYear, int endYear, int k) {
        if (k==0) {
            return null;
        }

        TreeMap returnmap = new TreeMap();
        for (String i : wordsMap.keySet()) {
            TimeSeries wordHistoryMap = countHistory(i, startYear, endYear);
            int sum = 0;
            for (Map.Entry<Integer, Double> entry : wordHistoryMap.entrySet()) {
                sum += entry.getValue();
            }
            returnmap.put(i, sum);
        }

        Comparator<String> valueComparator = new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                Integer v1 = (Integer) returnmap.get(s1);
                Integer v2 = (Integer) returnmap.get(s2);
                return v2.compareTo(v1);
            }
        };

        // Create a new TreeMap based on the sorted entries
        TreeMap<String, Integer> sortedTreeMap = new TreeMap<>(valueComparator);
        sortedTreeMap.putAll(returnmap);

        List<String> firstKKeys = new ArrayList<>();
        int count = 0;
        for (String key : sortedTreeMap.keySet()) {
            firstKKeys.add(key);
            count++;
            if (count == k) {
                break;
            }
        }

        return firstKKeys;
    }

    public TreeMap totalWords1(int startYear, int endYear) {

        TreeMap returnmap = new TreeMap();
        for (String i : wordsMap.keySet()) {
            TimeSeries wordHistoryMap = countHistory(i, startYear, endYear);
            int sum = 0;
            for (Map.Entry<Integer, Double> entry : wordHistoryMap.entrySet()) {
                sum += entry.getValue();
            }
            returnmap.put(i, sum);
        }
        Comparator<String> valueComparator = new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                Integer v1 = (Integer) returnmap.get(s1);
                Integer v2 = (Integer) returnmap.get(s2);
                return v2.compareTo(v1);
            }
        };

        // Create a new TreeMap based on the sorted entries
        TreeMap<String, Integer> sortedTreeMap = new TreeMap<>(valueComparator);
        sortedTreeMap.putAll(returnmap);
        return sortedTreeMap;
    }

}



