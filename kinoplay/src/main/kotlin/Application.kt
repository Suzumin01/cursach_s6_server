package com.example

import com.example.database.appointments.Appointments
import com.example.database.appointments.configureAppointmentsRouting
import com.example.database.branches.Branches
import com.example.database.branches.configureBranchesRouting
import com.example.database.specialists.Specialists
import com.example.database.specialists.configureSpecialistsRouting
import com.example.database.specialities.Specialities
import com.example.database.specialities.configureSpecialitiesRouting
import com.example.database.tokens.Tokens
import com.example.database.users.Users
import com.example.login.configureLoginRouting
import com.example.register.configureRegisterRouting
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/kinoplay",
        "org.postgresql.Driver",
        "postgres",
        "lom22otikin"
    )

    transaction {
        SchemaUtils.create(Users, Tokens, Specialists, Branches, Specialities, Appointments)
    }

    embeddedServer(CIO, port = 8081, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
    configureLoginRouting()
    configureRegisterRouting()
    configureBranchesRouting()
    configureSpecialistsRouting()
    configureSpecialitiesRouting()
    configureAppointmentsRouting()
}