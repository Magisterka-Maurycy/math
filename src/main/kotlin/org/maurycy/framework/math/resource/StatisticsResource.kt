package org.maurycy.framework.math.resource

import io.quarkus.logging.Log
import jakarta.annotation.security.RolesAllowed
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics
import org.maurycy.framework.math.model.StatisticInput
import org.maurycy.framework.math.model.StatisticOutput


@Path("/statistics")
class StatisticsResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("user", "admin")
    suspend fun getDescriptiveStatistics(x: StatisticInput): StatisticOutput {
        val stats = DescriptiveStatistics()
        x.inputArray.forEach { value ->
            stats.addValue(value)
        }
        Log.info(stats)

        return StatisticOutput(
            stats
        )
    }
}