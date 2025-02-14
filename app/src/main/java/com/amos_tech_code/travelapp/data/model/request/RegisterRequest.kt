package com.amos_tech_code.travelapp.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val email: String,
    val password: String,
    val name: String
)
