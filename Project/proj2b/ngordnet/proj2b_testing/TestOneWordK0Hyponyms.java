package ngordnet.proj2b_testing;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.ngrams.TimeSeries;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/** Tests the most basic case for Hyponyms where the list of words is one word long, and k = 0.*/
public class TestOneWordK0Hyponyms {
    // this case doesn't use the NGrams dataset at all, so the choice of files is irrelevant
    public static final String WORDS_FILE = "data/ngrams/very_short.csv";
    public static final String TOTAL_COUNTS_FILE = "data/ngrams/total_counts.csv";
    public static final String SMALL_SYNSET_FILE = "data/wordnet/synsets16.txt";
    public static final String SMALL_HYPONYM_FILE = "data/wordnet/hyponyms16.txt";

    @Test
    public void testActK0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("act");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
        String actual = studentHandler.handle(nq);
        String expected = "[act, action, change, demotion, human_action, human_activity, variation]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testWhole() {
        String w = "./data/ngrams/top_14377_words.csv";
        String c = "./data/ngrams/total_counts.csv";
        String s = "./data/wordnet/synsets.txt";
        String h = "./data/wordnet/hyponyms.txt";
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                w, c, s, h);
        List<String> words = List.of("whole");
        NgordnetQuery nq = new NgordnetQuery(words, 1470, 2019, 7);
        String actual = studentHandler.handle(nq);
        String expected = "[being, can, first, have, may, one, work]";
        assertThat(actual).isEqualTo(expected);
    }
}
