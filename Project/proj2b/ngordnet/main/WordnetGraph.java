package ngordnet.main;
import edu.princeton.cs.algs4.In;


import java.util.List;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Set;
import java.util.Map;
import java.util.TreeMap;
import java.util.Comparator;
import java.util.ArrayList;

import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;


public class WordnetGraph {
    private Set<String>[] array;
    private DirectedGraph graph;
    private NGramMap nGramMap;
    private Set hyponymsWithNoRestriction;
    private static final int LN = 82192;
    private static final int HN = 17161;
    public WordnetGraph(NGramMap ngm, String synsets, String hyponyms) {
        nGramMap = ngm;
        //int lineNum = readLineNum(synsets, hyponyms);
        array = new HashSet[LN];
        In inW = new In(synsets);
        while (inW.hasNextLine() && !inW.isEmpty()) {
            String[] str = inW.readLine().split(",");
            int index = Integer.parseInt(str[0]);
            String[] words = str[1].split(" ");
            array[index] = new HashSet();
            for (String s: words) {
                array[index].add(s);
            }
        }
        graph = new DirectedGraph(array.length);
        In inW2 = new In(hyponyms);
        while (inW2.hasNextLine() && !inW2.isEmpty()) {
            String[] str2 = inW2.readLine().split(",");
            int hypernym = Integer.parseInt(str2[0]);
            for (int i = 0; i < str2.length; i++) {
                int hyponym = Integer.parseInt(str2[i]);
                graph.addEdge(hypernym, hyponym);
            }
        }
    }

    public int readLineNum(String synsets, String hyponyms) {
        In inW3 = new In(synsets);
        int num = 0;
        while (!inW3.isEmpty()) {
            inW3.readLine();
            num++;
        }
        return num;
    }


    public HashSet findIndex(String s) {
        HashSet indexArray = new HashSet();
        for (int i = 0; i < array.length; i++) {
            for (String j: array[i]) {
                if (j.equals(s)) {
                    indexArray.add(i);
                }
            }
        }
        return indexArray;
    }

    public Set findOneIndex(int index) {
        Set lst = new HashSet<Integer>();
        return findOneIndex(index, lst);
    }

    public Set findOneIndex(int index, Set lst) {
        if (graph.adj(index).size() == 0) {
            for (String st: array[index]) {
                lst.add(st);
            }
            return lst;
        } else {
            List adj = graph.adj(index);

            for (int i = 1; i < adj.size(); i++) {
                lst.add(adj.get(i));
                lst = addAll(lst, findOneIndex((int) adj.get(i), new HashSet()));
            }
            lst.add(index);
            return lst;
        }
    }

    public Set addAll(Set lst1, Set lst2) {
        for (Object i: lst2) {
            lst1.add((int) i);
        }
        return lst1;
    }



    public TreeSet<Integer> findCommonElements(Set set1, Set set2) {
        TreeSet<Integer> commonElements = new TreeSet<>();

        // If the sets array is empty, return an empty HashSet
        if (set1.size() == 0 || set2.size() == 0) {
            return commonElements;
        }

        // Add all elements from the first set to the commonElements HashSet
        commonElements.addAll(set1);

        // Iterate over the rest of the sets and retain only elements that exist in commonElements
        commonElements.retainAll(set2);
        return commonElements;
    }

    public String[] outPut(List<String> str) {
        Set returnLst = new HashSet<String>();
        //Set lst = new TreeSet<String>();
        Set returnSet = new TreeSet<String>();

        int addCount = 0;
        for (String st: str) {
            for (Object i : findIndex(st)) {
                Set IndexSet = new HashSet<Integer>();
                IndexSet = addAll(IndexSet, findOneIndex((int) i));
                for (Object k: IndexSet) {
                    for (String s: array[(int) k]) {
                        returnLst.add(s);
                    }
                }
               // returnLst.addAll(findOneIndex((int) i));
            }
            if (addCount == 0) {
                returnSet.addAll(returnLst);
                addCount++;
            } else {
                returnSet = findCommonElements(returnSet, returnLst);
            }
            returnLst = new HashSet<Integer>();
        }

        String[] arr = (String[]) returnSet.toArray(new String[returnSet.size()]);
        hyponymsWithNoRestriction = returnSet;
        return arr;
    }

    public String[] outPut(List<String> str, int StartYear, int EndYear, int k) {
        outPut(str);
        String[] returnStringArray = totalWords(hyponymsWithNoRestriction, StartYear, EndYear, k);
        return returnStringArray;
    }

    public String[] totalWords(Set words, int startYear, int endYear, int k) {
        if (k==0) {
            return null;
        }

        TreeMap returnMap = new TreeMap();

        for (Object i : words) {
            TimeSeries wordHistoryMap = nGramMap.countHistory((String) i, startYear, endYear);
            Double sum = 0.0;
            for (Map.Entry<Integer, Double> entry : wordHistoryMap.entrySet()) {
                sum += entry.getValue();
            }
            returnMap.put(i, sum);
        }

        Comparator<String> valueComparator = new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                Double v1 = (Double) returnMap.get(s1);
                Double v2 = (Double) returnMap.get(s2);
                return v2.compareTo(v1);
            }
        };

        // Create a new TreeMap based on the sorted entries
        TreeMap<String, Double> sortedTreeMap = new TreeMap<>(valueComparator);
        sortedTreeMap.putAll(returnMap);

        Set firstKKeys = new TreeSet<>();
        int count = 0;

        for (Map.Entry<String, Double> entry: sortedTreeMap.entrySet()) {
            if (entry.getValue() > 0.0) {
                firstKKeys.add(entry.getKey());
            }
            count++;
            if (count == k) {
                break;
            }
        }
        String[] StringArr = (String[]) firstKKeys.toArray(new String[firstKKeys.size()]);
        return StringArr;
    }

}


