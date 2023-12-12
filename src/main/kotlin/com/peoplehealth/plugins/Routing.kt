package com.peoplehealth.plugins

import com.peoplehealth.controller.CoordsController
import com.peoplehealth.data.Exam
import com.peoplehealth.data.UserLevel
import com.peoplehealth.service.PatientService
import com.peoplehealth.service.UserService
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
        val patientservice by inject<PatientService>()
        val userService by inject<UserService>()

        val coordsController by inject<CoordsController>()

        post("/createPatient") {
            patientservice.savePatient(call.receive())
        }

        get("/coords") {
            call.respondText(Json.encodeToString(coordsController.getCoords()), ContentType.Application.Json, HttpStatusCode.OK)
            coordsController.getCoords().clear()
        }

        get("/findPatientByName") {
            call.parameters["name"]?.let { name ->
                patientservice.findPatientByName(name)?.let { patient ->
                    call.respond(patient)
                }
            } ?: call.respondText(text = "Name not found", status = HttpStatusCode.NotFound)
        }

        get("/findPatientByDocument") {
            call.parameters["document"]?.let { document ->
                patientservice.findPatientByDocument(document)?.let { patient ->
                    call.respond(patient)
                }
            } ?: call.respondText(text = "Document not found", status = HttpStatusCode.NotFound)
        }

        post("/saveExamsByPatientDocument") {
            call.parameters["document"]?.let { document ->
                call.receive<List<Exam>>().let { exams ->
                    patientservice.findPatientByDocument(document)?.let { patient ->
                        patient.get().exams.addAll(exams)
                        patientservice.savePatient(patient.get())
                        call.respondText(text = "Exams saved", status = HttpStatusCode.OK)
                    }
                    call.respondText(text = "Exams saved", status = HttpStatusCode.OK)
                }
            } ?: call.respondText(text = "Document not found", status = HttpStatusCode.NotFound)
        }

        post("/saveUser") {
            userService.saveUser(call.receive())
        }

        get("/findUserByName") {
            call.parameters["name"]?.let { name ->
                userService.findUserByName(name)?.let { user ->
                    call.respond(user)
                }
            } ?: call.respondText(text = "Name not found", status = HttpStatusCode.NotFound)
        }

        get("/findUserByDocument") {
            call.parameters["document"]?.let { document ->
                userService.findUserByDocument(document)?.let { user ->
                    call.respond(user)
                }
            } ?: call.respondText(text = "Document not found", status = HttpStatusCode.NotFound)
        }

        get("/findUsersByPermissionLevel") {
            call.parameters["permissionLevel"]?.let { permissionLevel ->
                userService.findUsersByPermissionLevel(UserLevel.valueOf(permissionLevel)).let { users ->
                    call.respond(users)
                }
            } ?: call.respondText(text = "Permission level not found", status = HttpStatusCode.NotFound)
        }
    }
}
