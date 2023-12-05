package com.peoplehealth.plugins

import com.peoplehealth.controller.CoordsController
import com.peoplehealth.data.Exam
import com.peoplehealth.data.UserLevel
import com.peoplehealth.repository.PatientRepository
import com.peoplehealth.repository.UserRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    routing {
        val patientRepository by inject<PatientRepository>()
        val userRepository by inject<UserRepository>()

        val coordsController by inject<CoordsController>()

        authenticate("auth-basic") {
            post("/createPatient") {
                patientRepository.savePatient(call.receive())
            }

            get("/coords") {
                call.respond(HttpStatusCode.OK, Json.encodeToString(coordsController.getCoords().toString()))
            }

            get("/findPatientByName") {
                call.parameters["name"]?.let { name ->
                    patientRepository.findPatientByName(name)?.let { patient ->
                        call.respond(patient)
                    } ?: call.respondText(text = "Patient not found", status = HttpStatusCode.NotFound)
                } ?: call.respondText(text = "Name not found", status = HttpStatusCode.NotFound)
            }

            get("/findPatientByDocument") {
                call.parameters["document"]?.let { document ->
                    patientRepository.findPatientByDocument(document)?.let { patient ->
                        call.respond(patient)
                    } ?: call.respondText(text = "Patient not found", status = HttpStatusCode.NotFound)
                } ?: call.respondText(text = "Document not found", status = HttpStatusCode.NotFound)
            }

            post("/saveExamsByPatientDocument") {
                call.parameters["document"]?.let { document ->
                    call.receive<List<Exam>>().let { exams ->
                        patientRepository.saveExamsByPatientDocument(document, exams)
                        call.respondText(text = "Exams saved", status = HttpStatusCode.OK)
                    }
                } ?: call.respondText(text = "Document not found", status = HttpStatusCode.NotFound)
            }

            post("/saveUser") {
                userRepository.saveUser(call.receive())
            }

            get("/findUserByName") {
                call.parameters["name"]?.let { name ->
                    userRepository.findUserByName(name)?.let { user ->
                        call.respond(user)
                    } ?: call.respondText(text = "User not found", status = HttpStatusCode.NotFound)
                } ?: call.respondText(text = "Name not found", status = HttpStatusCode.NotFound)
            }

            get("/findUserByDocument") {
                call.parameters["document"]?.let { document ->
                    userRepository.findUserByDocument(document)?.let { user ->
                        call.respond(user)
                    } ?: call.respondText(text = "User not found", status = HttpStatusCode.NotFound)
                } ?: call.respondText(text = "Document not found", status = HttpStatusCode.NotFound)
            }

            get("/findUsersByPermissionLevel") {
                call.parameters["permissionLevel"]?.let { permissionLevel ->
                    userRepository.findUsersByPermissionLevel(UserLevel.valueOf(permissionLevel)).let { users ->
                        call.respond(users)
                    }
                } ?: call.respondText(text = "Permission level not found", status = HttpStatusCode.NotFound)
            }
        }
    }
}
