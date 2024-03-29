package org.maurycy.framework.math.resource

import io.quarkus.test.common.http.TestHTTPEndpoint
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.security.TestSecurity
import io.restassured.RestAssured
import jakarta.ws.rs.core.MediaType
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.maurycy.framework.math.enums.Decomposition
import org.maurycy.framework.math.model.EquationInput

@QuarkusTest
@TestHTTPEndpoint(EquationResource::class)
class EquationResourceTest {
    val coefficient1 = listOf(1.0, 0.0)
    val coefficient2 = listOf(0.0, 1.0)

    val singularCoefficient1 = listOf(3.0, 6.0)
    val singularCoefficient2 = listOf(2.0, 4.0)

    @Test
    @TestSecurity(user = "testUser", roles = ["admin", "user"])
    fun solveFailWhenEmptyInput() {
        RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON)
            .body("")
            .`when`()
            .post()
            .then()
            .statusCode(400)
            .body(
                CoreMatchers.`is`("{\"exceptionMessage\":\"Equation Input is null\"}")
            )
    }

    @ParameterizedTest
    @EnumSource(Decomposition::class)
    @TestSecurity(user = "testUser", roles = ["admin", "user"])
    fun solveNonSquareMatrix(decomposition: Decomposition) {
        val validatableResponse = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                EquationInput(
                    coefficients = listOf(coefficient1),
                    constants = listOf(1.0, 1.0),
                    decomposition = decomposition
                )
            )
            .`when`().post()
            .then()
            .statusCode(400)
        val situation1 = arrayOf(
            Decomposition.LUDecomposition,
            Decomposition.CholeskyDecomposition,
            Decomposition.EigenDecomposition
        )
        val situationRest = Decomposition.values().dropWhile {
            situation1.contains(it)
        }

        if (situation1.contains(decomposition)) {
            validatableResponse.body(
                CoreMatchers.`is`("{\"exceptionMessage\":\"non square (1x2) matrix\"}")
            )
        }
        if (situationRest.contains(decomposition)) {
            validatableResponse.body(
                CoreMatchers.`is`("{\"exceptionMessage\":\"2 != 1\"}")
            )
        }
    }

    @ParameterizedTest
    @EnumSource(Decomposition::class)
    @TestSecurity(user = "testUser", roles = ["admin", "user"])
    fun solveSingularMatrix(decomposition: Decomposition) {
        val situation1 = arrayOf(Decomposition.LUDecomposition, Decomposition.EigenDecomposition)
        val situation2 = arrayOf(Decomposition.CholeskyDecomposition)

        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                EquationInput(
                    coefficients = listOf(singularCoefficient1, singularCoefficient2),
                    constants = listOf(1.0, 1.0),
                    decomposition = decomposition
                )
            )
            .`when`().post()

        when (decomposition) {
            in situation1 -> {
                response.then()
                    .statusCode(400)
                    .body(
                        CoreMatchers.`is`("{\"exceptionMessage\":\"matrix is singular\"}")
                    )
            }

            in situation2 -> {
                response.then()
                    .statusCode(400)
                    .body(
                        CoreMatchers.`is`("{\"exceptionMessage\":\"non symmetric matrix: the difference between entries at (0,1) and (1,0) is larger than 0\"}")
                    )
            }

            else -> {
                response.then()
                    .statusCode(200)
                    .body(
                        CoreMatchers.containsString("solution")
                    )
            }
        }
    }

    @ParameterizedTest
    @EnumSource(Decomposition::class)
    @TestSecurity(user = "testUser", roles = ["admin", "user"])
    fun solve(decomposition: Decomposition) {
        RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                EquationInput(
                    coefficients = listOf(coefficient1, coefficient2),
                    constants = listOf(1.0, 1.0),
                    decomposition = decomposition
                )
            )
            .`when`().post()
            .then()
            .statusCode(200)
            .body(
                CoreMatchers.`is`("{\"solution\":[1.0,1.0]}")
            )
    }
}