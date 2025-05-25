package com.example.database.branches

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Branches : Table("branches") {
    val id: Column<Int> = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val address = varchar("address", 255)

    override val primaryKey = PrimaryKey(id)

    fun insert(branch: BranchDTO) {
        transaction {
            insert {
                it[name] = branch.name
                it[address] = branch.address
            }
        }
    }

    fun getAll(): List<BranchDTO> = transaction {
        selectAll().map {
            BranchDTO(
                id = it[Branches.id],
                name = it[Branches.name],
                address = it[Branches.address]
            )
        }
    }
}