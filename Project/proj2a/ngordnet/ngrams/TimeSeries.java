package ngordnet.ngrams;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    private static final int MIN_YEAR = 1400;
    private static final int MAX_YEAR = 2100;

    private double track;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        for (Integer year: ts.years()) {
            if (startYear <= year && year <= endYear) {
                this.put(year, ts.get(year));
            }
        }
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        ArrayList<Integer> returnList = new ArrayList<Integer>();
        for (Integer year: keySet()) {
            returnList.add(year);
        }
        return returnList;
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        ArrayList<Double> returnList = new ArrayList<Double>();
        for (Integer year: years()) {
            track = get(year);
            returnList.add(track);
        }
        return returnList;
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries t = new TimeSeries();
        List<Integer> years = years();
        years.addAll(ts.years());
        for (Integer year: years) {
            Double thisValue = this.get(year);
            Double tsValue = ts.get(year);
            if (thisValue == null) {
                t.put(year, tsValue);
            } else if (tsValue == null) {
                t.put(year, thisValue);
            } else {
                t.put(year, thisValue + tsValue);
            }
        }
        return t;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries t = new TimeSeries();
        for (int year: years()) {
            Double thisValue = this.get(year);
            Double tsValue = ts.get(year);
            if (tsValue == null) {
                throw new IllegalArgumentException();
            }
            t.put(year, thisValue / tsValue);
        }
        return t;
    }


    public TimeSeries(TimeSeries ts) {
        for (Integer year : ts.years()) {
            this.put(year, ts.get(year));
        }
    }
}
