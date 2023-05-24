package org.maurycy.framework.math.resource

import jakarta.annotation.security.RolesAllowed
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.maurycy.framework.math.exception.EquationInputNullException
import org.maurycy.framework.math.model.EquationAnswer
import org.maurycy.framework.math.model.EquationInput
import org.maurycy.framework.math.service.EquationService

@Path("/equation")
class EquationResource(
    private val equationService: EquationService
){

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("user", "admin")
    suspend fun solve(aInput: EquationInput?): EquationAnswer {
        return aInput?.let { equationService.solve(aInput = it) } ?: throw EquationInputNullException()
    }


}