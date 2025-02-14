package com.amos_tech_code.travelapp.ui.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amos_tech_code.travelapp.cache.TravelAppSession
import com.amos_tech_code.travelapp.data.NetworkService
import com.amos_tech_code.travelapp.data.ResultWrapper
import com.amos_tech_code.travelapp.data.model.request.LoginRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    val networkService: NetworkService,
    val session: TravelAppSession
): ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Nothing)
    val loginState = _loginState.asStateFlow()

    fun login(email: String, password: String) {

        _loginState.value = LoginState.Loading

        viewModelScope.launch {
            val result = networkService.login(LoginRequest(
                email = email,
                password = password
            ))

            when(result) {
                is ResultWrapper.Error -> {
                    _loginState.value = LoginState.Error(result.e.message?: "Something went wrong")
                }
                is ResultWrapper.Success -> {
                    _loginState.value = LoginState.Success
                    session.saveToken(result.value.token)
                    session.saveUserName(result.value.user.name)
                }
            }
        }
    }

    fun resetLoginState() {
        _loginState.value = LoginState.Nothing
    }
}




sealed class LoginState {

    data object Nothing: LoginState()

    data object Loading: LoginState()

    data object Success: LoginState()

    data class Error(val error: String): LoginState()
}