package org.maurycy.framework.math.exception.mapper

import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import org.maurycy.framework.math.exception.EquationInputNullException
import org.maurycy.framework.math.model.ExceptionDto

@Provider
class EquationInputNullExceptionMapper : ExceptionMapper<EquationInputNullException> {
    override fun toResponse(exception: EquationInputNullException): Response {
        return Response.status(Response.Status.BAD_REQUEST).entity(ExceptionDto(exception.localizedMessage)).build()
    }
}