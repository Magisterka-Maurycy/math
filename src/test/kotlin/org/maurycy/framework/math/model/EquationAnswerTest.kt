package org.maurycy.framework.math.model

import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Test
import org.wildfly.common.Assert

@QuarkusTest
class EquationAnswerTest {
    private val solution1 = listOf(5.0, 4.0, 3.0)
    private val solution2 = listOf(5.0, 4.0, 3.0)
    private val solution3 = listOf(5.0, 4.0, 3.1)

    @Test
    fun testEquals1() {
        Assert.assertTrue(EquationAnswer(solution1) == EquationAnswer(solution1))
    }

    @Test
    fun testEquals2() {
        Assert.assertTrue(EquationAnswer(solution1) == EquationAnswer(solution2))
    }

    @Test
    fun testEquals3() {
        Assert.assertFalse(EquationAnswer(solution1) == EquationAnswer(solution3))
    }

    @Test
    fun testHashCode1() {
        Assert.assertTrue(EquationAnswer(solution1).hashCode() == EquationAnswer(solution1).hashCode())
    }

    @Test
    fun testHashCode2() {
        Assert.assertTrue(EquationAnswer(solution1).hashCode() == EquationAnswer(solution2).hashCode())
    }

    @Test
    fun testHashCode3() {
        Assert.assertFalse(EquationAnswer(solution1).hashCode() == EquationAnswer(solution3).hashCode())
    }
}