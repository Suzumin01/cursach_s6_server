package com.example.database.specialities

import kotlinx.serialization.Serializable

@Serializable
data class SpecialityDTO(
    val id: Int = 0,
    val name: String
)