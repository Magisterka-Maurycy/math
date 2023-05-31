package org.maurycy.framework.math.resource

import io.quarkus.test.common.http.TestHTTPEndpoint
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.security.TestSecurity
import io.restassured.RestAssured
import jakarta.ws.rs.core.MediaType
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Test
import org.maurycy.framework.math.model.StatisticInput


@QuarkusTest
@TestHTTPEndpoint(StatisticsResource::class)
class StatisticsResourceTest {

    @Test
    @TestSecurity(user = "testUser", roles = ["admin", "user"])
    fun getDescriptiveStatisticsTest() {
        RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                StatisticInput(
                    inputArray = listOf(0.0, 0.1, 1.0, 2.2, 3.4, 4.5, 5.7, 6.9)
                )
            )
            .`when`().post()
            .then()
            .statusCode(200)
            .body(
                CoreMatchers.containsString("mean"),
                CoreMatchers.containsString("standardDeviation"),
                CoreMatchers.containsString("median"),
                CoreMatchers.containsString("max"),
                CoreMatchers.containsString("min"),
                CoreMatchers.containsString("skewness"),
                CoreMatchers.containsString("kurtosis"),
            )
    }

}