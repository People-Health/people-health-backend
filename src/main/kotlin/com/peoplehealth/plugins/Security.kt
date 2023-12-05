package com.peoplehealth.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureSecurity() {
    authentication {
        basic("auth-basic") {
            validate {
                if (it.name == "user" && it.password == "password") {
                    UserIdPrincipal(it.name)
                } else {
                    null
                }
            }
        }
    }
}
