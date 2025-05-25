package com.example.database.specialists

import com.example.database.branches.Branches
import com.example.database.specialities.Specialities
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Specialists : Table("specialists") {
    val id: Column<Int> = integer("id").autoIncrement()
    val firstName = varchar("first_name", 100)
    val lastName = varchar("last_name", 100)
    val middleName = varchar("middle_name", 100).nullable()
    val specialityId = integer("speciality_id").references(Specialities.id)
    val branchId = integer("branch_id").references(Branches.id)

    override val primaryKey = PrimaryKey(id)

    fun insert(specialist: SpecialistDTO) {
        transaction {
            insert {
                it[firstName] = specialist.firstName
                it[lastName] = specialist.lastName
                it[middleName] = specialist.middleName
                it[specialityId] = specialist.specialityId
                it[branchId] = specialist.branchId
            }
        }
    }

    fun getAll(): List<SpecialistDTO> = transaction {
        selectAll().map {
            SpecialistDTO(
                id = it[Specialists.id],
                firstName = it[firstName],
                lastName = it[lastName],
                middleName = it[middleName],
                specialityId = it[specialityId],
                branchId = it[branchId]
            )
        }
    }
}
