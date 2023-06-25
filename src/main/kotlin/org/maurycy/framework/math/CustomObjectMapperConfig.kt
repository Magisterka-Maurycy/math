package org.maurycy.framework.math

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Singleton
import jakarta.ws.rs.Produces

@ApplicationScoped
class CustomObjectMapperConfig {
    @Singleton
    @Produces
    fun objectMapper(): ObjectMapper {
        return ObjectMapper().registerModule(KotlinModule.Builder().build())
    }
}