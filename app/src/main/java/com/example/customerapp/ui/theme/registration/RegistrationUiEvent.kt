package com.example.customerapp.ui.theme.registration

import com.example.customerapp.model.RegistrationResponse

sealed class RegistrationUiEvent {
    data class NavigateToSuccess(
        val registrationResponse: RegistrationResponse
    ) : RegistrationUiEvent()
    data class ShowError(val message: String) : RegistrationUiEvent()
}