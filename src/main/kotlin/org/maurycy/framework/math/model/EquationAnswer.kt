package org.maurycy.framework.math.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.quarkus.runtime.annotations.RegisterForReflection

@RegisterForReflection
data class EquationAnswer(
    @field:JsonProperty("solution")
    val solution: List<Double> = emptyList()
)