package ngordnet.main;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;

import java.util.Arrays;
import java.util.List;


public class HyponymsHandler extends NgordnetQueryHandler {
    private WordnetGraph w;

    private String hyponyms;
    private String synsets;
    private static final int SY = 1900;
    private static final int EY = 2020;


    public HyponymsHandler(WordnetGraph w1, String s3, String s4) {
        w = w1;
        synsets = s3;
        hyponyms = s4;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        int k = q.k();

        //WordnetGraph w = new WordnetGraph(ngMap, synsets, hyponyms);
        if (k == 0) {
            String[] str = w.outPut(words);
            String strings = Arrays.toString(str);
            return strings;
        }
        if (k != 0 && startYear == 0 && endYear == 0) {
            startYear = SY;
            endYear = EY;
            String[] str = w.outPut(words, startYear, endYear, k);
            String strings = Arrays.toString(str);
            return strings;
        } else {
            String[] str = w.outPut(words, startYear, endYear, k);
            String strings = Arrays.toString(str);
            return strings;
        }
    }
}
