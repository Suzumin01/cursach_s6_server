package com.example.database.appointments

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class AppointmentDTO(
    val id: Int = 0,
    val userLogin: String,
    val specialistId: Int,
    val dateTime: String, // YYYY-MM-DDTHH:MM
    val status: String // pending, completed, cancelled
)