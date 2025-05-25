package com.example.database.specialists

import kotlinx.serialization.Serializable

@Serializable
data class SpecialistDTO(
    val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val middleName: String? = null,
    val specialityId: Int,
    val branchId: Int
)

