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
        return EquationAnswer(solver.solve(ArrayRealVector(aInput.constants.toTypedArray())).toArray().toList())
    }

    private fun decompositionSolver(aInput: EquationInput): DecompositionSolver {
        val coef = aInput.coefficients.map {
            it.toDoubleArray()
        }.toTypedArray()
       return when (aInput.decomposition) {

            Decomposition.LUDecomposition -> {
                LUDecomposition(Array2DRowRealMatrix(coef)).solver
            }

            Decomposition.CholeskyDecomposition -> {
                CholeskyDecomposition(Array2DRowRealMatrix(coef)).solver
            }

            Decomposition.EigenDecomposition -> {
                EigenDecomposition(Array2DRowRealMatrix(coef)).solver

            }

            Decomposition.QRDecomposition -> {
                QRDecomposition(Array2DRowRealMatrix(coef)).solver

            }

            Decomposition.RRQRDecomposition -> {
                RRQRDecomposition(Array2DRowRealMatrix(coef)).solver
            }

            Decomposition.SingularValueDecomposition -> {
                SingularValueDecomposition(Array2DRowRealMatrix(coef)).solver
            }
        }
    }
}