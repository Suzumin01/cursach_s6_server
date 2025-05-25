package com.example.database.branches

import com.example.database.branches.Branches
import com.example.database.branches.BranchDTO
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureBranchesRouting() {
    routing {
        route("/branches") {

            get {
                val allBranches = Branches.getAll()
                call.respond(allBranches)
            }

            get("{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respondText("Invalid branch ID", status = io.ktor.http.HttpStatusCode.BadRequest)
                    return@get
                }

                val branch = Branches.getAll().find { it.id == id }
                if (branch == null) {
                    call.respondText("Branch not found", status = io.ktor.http.HttpStatusCode.NotFound)
                } else {
                    call.respond(branch)
                }
            }

            post {
                val branch = call.receive<BranchDTO>()
                Branches.insert(branch)
                call.respondText("Branch created successfully")
            }
        }
    }
}