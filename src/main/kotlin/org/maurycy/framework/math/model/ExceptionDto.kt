package org.maurycy.framework.math.model

import io.quarkus.runtime.annotations.RegisterForReflection

@RegisterForReflection
data class ExceptionDto(val exceptionMessage: String)