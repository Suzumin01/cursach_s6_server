package com.example.database.specialities

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureSpecialitiesRouting() {
    routing {
        route("/specialities") {

            get {
                val all = Specialities.getAll()
                call.respond(all)
            }

            post {
                val speciality = call.receive<SpecialityDTO>()
                Specialities.insert(speciality)
                call.respondText("Speciality created successfully")
            }
        }
    }
}