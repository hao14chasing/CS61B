package ngordnet.main;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;

import java.util.List;



public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap myMap;
    private static String added;
    public HistoryTextHandler(NGramMap map) {
        myMap = map;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        String response = "";
        for (String word: words) {
            added = myMap.weightHistory(word, startYear, endYear).toString();
            response += word + ": " + added + "\n";
        }
        return response;
    }


}
