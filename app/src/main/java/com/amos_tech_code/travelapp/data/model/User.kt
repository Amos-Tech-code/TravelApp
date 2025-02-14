package com.amos_tech_code.travelapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val email: String,
    val id: Int,
    val name: String
)