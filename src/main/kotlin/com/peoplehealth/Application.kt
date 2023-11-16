package com.peoplehealth

import com.peoplehealth.factory.MongoConnectionFactory
import com.peoplehealth.plugins.*
import com.peoplehealth.repository.PatientRepository
import com.peoplehealth.repository.impl.PatientRepositoryImpl
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureKoin()
    configureSecurity()
    configureSockets()
    configureRouting()
}

fun Application.configureKoin() = install(Koin) {
    val appModule = module {
        single<MongoConnectionFactory> { MongoConnectionFactory(
            databaseName = "peoplehealth",
        ) }
        single<PatientRepository> { PatientRepositoryImpl(get()) }
    }
    modules(appModule)
}
