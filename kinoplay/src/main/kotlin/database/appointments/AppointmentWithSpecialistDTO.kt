package com.example.database.appointments

import kotlinx.serialization.Serializable

@Serializable
data class AppointmentWithSpecialistDTO(
    val id: Int,
    val userLogin: String,
    val dateTime: String,
    val status: String,
    val specialistId: Int,
    val specialistFullName: String,
    val speciality: String,
    val branchName: String
)