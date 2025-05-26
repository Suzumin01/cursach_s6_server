package com.example.database.appointments

import com.example.database.branches.Branches
import com.example.database.specialists.Specialists
import com.example.database.specialities.Specialities
import com.example.database.users.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Appointments : Table("appointments") {
    val id: Column<Int> = integer("id").autoIncrement()
    val userLogin = varchar("user_login", 25).references(Users.login)
    val specialistId = integer("specialist_id").references(Specialists.id)
    val dateTime = varchar("date_time", 50)
    val status = varchar("status", 20)

    override val primaryKey = PrimaryKey(id)

    fun insert(appointment: AppointmentDTO) {
        transaction {
            insert {
                it[userLogin] = appointment.userLogin
                it[specialistId] = appointment.specialistId
                it[dateTime] = appointment.dateTime
                it[status] = appointment.status
            }
        }
    }

    fun getAll(): List<AppointmentDTO> = transaction {
        selectAll().map {
            AppointmentDTO(
                id = it[Appointments.id],
                userLogin = it[userLogin],
                specialistId = it[specialistId],
                dateTime = it[dateTime],
                status = it[status]
            )
        }
    }

    //хз работает ли
    fun getByUserLogin(login: String): List<AppointmentDTO> = transaction {
        Appointments.selectAll()
            .where { userLogin eq login }
            .map {
                AppointmentDTO(
                    id = it[Appointments.id],
                    userLogin = it[userLogin],
                    specialistId = it[specialistId],
                    dateTime = it[dateTime],
                    status = it[status]
                )
            }
    }

    fun updateStatus(appointmentId: Int, newStatus: String) {
        transaction {
            Appointments.update({ Appointments.id eq appointmentId }) {
                it[status] = newStatus
            }
        }
    }

    fun getDetailedByUserLogin(login: String): List<AppointmentWithSpecialistDTO> = transaction {
        (Appointments innerJoin Specialists innerJoin Specialities innerJoin Branches)
            .selectAll().where { userLogin eq login }
            .map {
                val specialist = Specialists.alias("s")
                val fullName = "${it[Specialists.lastName]} ${it[Specialists.firstName]} ${it[Specialists.middleName] ?: ""}".trim()

                AppointmentWithSpecialistDTO(
                    id = it[Appointments.id],
                    userLogin = it[userLogin],
                    specialistId = it[specialistId],
                    dateTime = it[dateTime],
                    status = it[status],
                    specialistFullName = fullName,
                    speciality = it[Specialities.name],
                    branchName = it[Branches.name]
                )
            }
    }
}