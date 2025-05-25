package com.example.database.appointments

import com.example.database.users.Users
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureAppointmentsRouting() {
    routing {
        route("/appointments") {

            get {
                val allAppointments = Appointments.getAll()
                call.respond(allAppointments)
            }

            get("/{login}") {
                val login = call.parameters["login"]

                if (login.isNullOrBlank()) {
                    call.respond(HttpStatusCode.BadRequest, "Missing user login")
                    return@get
                }

                val user = Users.fetchUser(login)
                if (user == null) {
                    call.respond(HttpStatusCode.NotFound, "User not found")
                    return@get
                }

                val userAppointments = Appointments.getByUserLogin(login)
                call.respond(userAppointments)
            }

            post {
                val appointment = call.receive<AppointmentDTO>()

                val user = Users.fetchUser(appointment.userLogin)
                if (user == null) {
                    call.respond(HttpStatusCode.Unauthorized, "User not authorized")
                    return@post
                }

                Appointments.insert(appointment)
                call.respondText("Appointment created successfully")
            }

            put("/{id}/status") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Missing or invalid appointment ID")
                    return@put
                }

                val params = call.receiveParameters()
                val newStatus = params["status"]

                if (newStatus.isNullOrBlank()) {
                    call.respond(HttpStatusCode.BadRequest, "Missing status")
                    return@put
                }

                Appointments.updateStatus(id, newStatus)
                call.respondText("Status updated successfully")
            }
        }
    }
}