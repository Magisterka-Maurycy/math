package org.maurycy.framework.math.model

import org.maurycy.framework.math.enums.Decomposition


data class EquationInput(
    val coefficients: List<List<Double>>,
    val constants: List<Double>,
    val decomposition: Decomposition
)


