package ngordnet.main;

import ngordnet.browser.NgordnetServer;
import ngordnet.ngrams.NGramMap;

public class Main {
    public static void main(String[] args) {
        NgordnetServer hns = new NgordnetServer();
        String wordFile = "./data/ngrams/top_14377_words.csv";
        String countFile = "./data/ngrams/total_counts.csv";

        String synsetFile = "./data/wordnet/synsets.txt";
        String hyponymFile = "./data/wordnet/hyponyms.txt";


        NGramMap ngm = new NGramMap(wordFile, countFile);
        WordnetGraph wordnetGraph = new WordnetGraph(ngm, synsetFile, hyponymFile);
        HyponymsHandler newOne = new HyponymsHandler(wordnetGraph, synsetFile, hyponymFile);

        hns.startUp();
        hns.register("hyponyms", newOne);
        hns.register("history", new HistoryHandler(ngm));
        hns.register("historytext", new HistoryTextHandler(ngm));
    }
}
