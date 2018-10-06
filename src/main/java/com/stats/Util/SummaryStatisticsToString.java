package com.stats.Util;

import java.util.DoubleSummaryStatistics;

/**
 * Created by Mubashir on 9/9/2018.
 */
public class SummaryStatisticsToString {

    public static String toString(DoubleSummaryStatistics stats) {
        return String.format(
                "{\"sum\": \"%.2f\", \"avg\": \"%.2f\", \"max\": \"%.2f\", \"min\": \"%.2f\", \"count\": %d}",
                stats.getSum(),
                stats.getAverage(),
                stats.getMax() != Double.NEGATIVE_INFINITY ? stats.getMax() : Double.valueOf(0.00),
                stats.getMin() != Double.POSITIVE_INFINITY ? stats.getMin() : Double.valueOf(0.00),
                stats.getCount());
    }
}
