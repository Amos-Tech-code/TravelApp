package com.amos_tech_code.travelapp.ui.feature.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amos_tech_code.travelapp.cache.TravelAppSession
import com.amos_tech_code.travelapp.data.NetworkService
import com.amos_tech_code.travelapp.data.ResultWrapper
import com.amos_tech_code.travelapp.data.model.request.RegisterRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val networkService: NetworkService,
    private val session: TravelAppSession
): ViewModel() {

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Nothing)
    val registerState = _registerState.asStateFlow()

    fun register(name: String, email: String, password: String) {
        _registerState.value = RegisterState.Loading
        // Perform registration logic here
        viewModelScope.launch {
            val result = networkService.register(
                RegisterRequest(
                    email = email,
                    password = password,
                    name = name
                )
            )

            when(result) {
                is ResultWrapper.Success -> {
                    _registerState.value = RegisterState.Success
                    session.saveToken(result.value.token)
                    session.saveUserName(result.value.user.name)
                }
                is ResultWrapper.Error -> {
                    _registerState.value = RegisterState.Error(result.e.message?: "Something went wrong")
                }
            }
        }

    }

    fun resetState() {
        _registerState.value = RegisterState.Nothing
    }

}


sealed class RegisterState {

    data object Nothing: RegisterState()

    data object Loading: RegisterState()

    data object Success: RegisterState()

    data class Error(val error: String): RegisterState()
}