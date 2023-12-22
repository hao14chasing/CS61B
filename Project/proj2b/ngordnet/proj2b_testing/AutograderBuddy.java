package ngordnet.proj2b_testing;

import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.main.HyponymsHandler;
import ngordnet.main.WordnetGraph;
import ngordnet.ngrams.NGramMap;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {

        NGramMap ngm = new NGramMap(wordFile, countFile);
        WordnetGraph wordnetGraph = new WordnetGraph(ngm, synsetFile, hyponymFile);

        return new HyponymsHandler(wordnetGraph, synsetFile, hyponymFile);
        // return null originally
    }
}
