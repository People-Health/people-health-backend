package com.peoplehealth

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.peoplehealth.controller.CoordsController
import com.peoplehealth.factory.MongoConnectionFactory
import com.peoplehealth.plugins.configureRouting
import com.peoplehealth.plugins.configureSecurity
import com.peoplehealth.repository.PatientRepository
import com.peoplehealth.repository.impl.PatientRepositoryImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import org.koin.dsl.module
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin

import java.time.Duration

fun main() {
    embeddedServer(Netty, port = 8081, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureKoin()
    configureCors()
    configureJson()
    configureSecurity()
    configureRouting()
    configureSockets()
}

fun Application.configureJson() = install(ContentNegotiation) {
    jacksonObjectMapper()
}

fun Application.configureCors() = install(CORS) {
    exposeHeader("key")
    allowMethod(HttpMethod.Get)
    allowMethod(HttpMethod.Post)
    allowMethod(HttpMethod.Put)
    allowHeader(HttpHeaders.AccessControlAllowOrigin)
    allowHeader(HttpHeaders.ContentType)
    allowSameOrigin = true
    allowCredentials = true
    anyHost()
}

fun Application.configureKoin() = install(Koin) {
    val appModule = module {
        single<MongoConnectionFactory> {
            MongoConnectionFactory(
                databaseName = "peoplehealth",
            )
        }
        single<PatientRepository> { PatientRepositoryImpl(get()) }
        single<CoordsController> { CoordsController() }
    }
    modules(appModule)
}

fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    val coordsController by inject<CoordsController>()

    routing {
        webSocket("/ws") {
            for (frame in incoming) {
                if (frame is Frame.Text) {
                    coordsController.addCoords(frame.readText())
                    println(coordsController.getCoords())
                    println("received ->>> $frame")
                }
            }
        }
    }
}