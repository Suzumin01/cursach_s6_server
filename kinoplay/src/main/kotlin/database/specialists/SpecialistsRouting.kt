package com.example.database.specialists

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureSpecialistsRouting() {
    routing {
        route("/specialists") {

            get {
                val specialists = Specialists.getAll()
                call.respond(specialists)
            }

            post {
                val specialist = call.receive<SpecialistDTO>()
                Specialists.insert(specialist)
                call.respondText("Specialist created successfully")
            }
        }
    }
}