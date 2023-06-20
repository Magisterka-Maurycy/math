package org.maurycy.framework.math.service


import jakarta.enterprise.context.ApplicationScoped
import org.apache.commons.math3.linear.Array2DRowRealMatrix
import org.apache.commons.math3.linear.ArrayRealVector
import org.apache.commons.math3.linear.CholeskyDecomposition
import org.apache.commons.math3.linear.DecompositionSolver
import org.apache.commons.math3.linear.EigenDecomposition
import org.apache.commons.math3.linear.LUDecomposition
import org.apache.commons.math3.linear.QRDecomposition
import org.apache.commons.math3.linear.RRQRDecomposition
import org.apache.commons.math3.linear.SingularValueDecomposition
import org.maurycy.framework.math.enums.Decomposition
import org.maurycy.framework.math.model.EquationAnswer
import org.maurycy.framework.math.model.EquationInput

@ApplicationScoped
class EquationService {

    fun solve(aInput: EquationInput): EquationAnswer {
        val solver = decompositionSolver(aInput)
        val solution = solver.solve(ArrayRealVector(aInput.constants.toTypedArray()))
        val solutionList = solution.toArray().toList()
        return EquationAnswer(solutionList)
    }

    private fun decompositionSolver(aInput: EquationInput): DecompositionSolver {
        val coef = aInput.coefficients.map {
            it.toTypedArray().toDoubleArray()
        }.toTypedArray()
        val matrix = Array2DRowRealMatrix(coef)
        return when (aInput.decomposition) {
            Decomposition.LUDecomposition -> {
                LUDecomposition(matrix).solver
            }

            Decomposition.CholeskyDecomposition -> {
                CholeskyDecomposition(matrix).solver
            }

            Decomposition.EigenDecomposition -> {
                EigenDecomposition(matrix).solver
            }

            Decomposition.QRDecomposition -> {
                QRDecomposition(matrix).solver
            }

            Decomposition.RRQRDecomposition -> {
                RRQRDecomposition(matrix).solver
            }

            Decomposition.SingularValueDecomposition -> {
                SingularValueDecomposition(matrix).solver
            }
        }
    }
}