package com.amos_tech_code.travelapp.data.model.response

import com.amos_tech_code.travelapp.data.model.User
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val token: String,
    val user: User
)