package org.maurycy.framework.math.model

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics

data class StatisticOutput(
    val mean: Double,
    val standardDeviation: Double,
    val median: Double,
    val max: Double,
    val min: Double,
    val skewness: Double,
    val kurtosis: Double
) {
    constructor(stats: DescriptiveStatistics) : this(
        mean = stats.mean, standardDeviation = stats.standardDeviation,
        median = stats.getPercentile(50.0),
        max = stats.max,
        min = stats.min,
        skewness = stats.skewness,
        kurtosis = stats.kurtosis
    )
}
