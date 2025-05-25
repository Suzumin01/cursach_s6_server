package com.example.database.branches

import kotlinx.serialization.Serializable

@Serializable
data class BranchDTO(
    val id: Int = 0,
    val name: String,
    val address: String
)