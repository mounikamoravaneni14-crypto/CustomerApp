package com.example.customerapp.model

data class RegistrationUIState(
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",
    val city: String = "",

    val fullNameError: String? = null,
    val emailError: String? = null,
    val phoneError: String? = null,
    val cityError: String? = null,

    val isLoading: Boolean = false
)

data class RegistrationRequest(
    val name: String,
    val email: String,
    val phone: String,
    val city: String,
)

data class RegistrationResponse(
    val name: String?,
    val email: String?,
    val phone :String?,
    val city : String?
)

data class ApiError(
    val error: String? = null,
    val msg: String? = null
)