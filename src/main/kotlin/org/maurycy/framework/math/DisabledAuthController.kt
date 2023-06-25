package org.maurycy.framework.math

import io.quarkus.arc.profile.UnlessBuildProfile
import io.quarkus.logging.Log
import io.quarkus.runtime.StartupEvent
import io.quarkus.security.spi.runtime.AuthorizationController
import jakarta.annotation.Priority

import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Observes
import jakarta.enterprise.inject.Alternative
import jakarta.interceptor.Interceptor
import org.eclipse.microprofile.config.inject.ConfigProperty


@Alternative
@Priority(Interceptor.Priority.LIBRARY_AFTER)
@ApplicationScoped
// tests have their own mechanism for disabling authorization
@UnlessBuildProfile("test")
class DisabledAuthController : AuthorizationController() {
    @ConfigProperty(name = "disable.authorization", defaultValue = "false")
    var disableAuthorization = false

    fun onStartUP(@Observes ev: StartupEvent) {
        Log.info("disableAuthorization is set to: $disableAuthorization")
    }

    override fun isAuthorizationEnabled(): Boolean {
        Log.info("disableAuthorization is set to: $disableAuthorization")
        return !disableAuthorization
    }
}