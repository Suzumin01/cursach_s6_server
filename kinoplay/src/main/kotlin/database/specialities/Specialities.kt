package com.example.database.specialities

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Specialities : Table("specialities") {
    val id: Column<Int> = integer("id").autoIncrement()
    val name = varchar("name", 100)

    override val primaryKey = PrimaryKey(id)

    fun insert(specialityDTO: SpecialityDTO) {
        transaction {
            insert {
                it[name] = specialityDTO.name
            }
        }
    }

    fun getAll(): List<SpecialityDTO> = transaction {
        selectAll().map {
            SpecialityDTO(
                id = it[Specialities.id],
                name = it[name]
            )
        }
    }
}