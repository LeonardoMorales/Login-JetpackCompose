package dev.leonardom.loginjetpackcompose.presentation.login

import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.leonardom.loginjetpackcompose.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    val state: MutableState<LoginState> = mutableStateOf(LoginState())

    fun login(email: String, password: String) {
        val errorMessage = if(email.isBlank() || password.isBlank()) {
            R.string.error_input_empty
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            R.string.error_not_a_valid_email
        } else if(email != "user@gmail.com" || password != "password"){
            R.string.error_invalid_credentials
        } else null

        errorMessage?.let {
            state.value = state.value.copy(errorMessage = it)
            return
        }

        viewModelScope.launch {
            state.value = state.value.copy(displayProgressBar = true)

            delay(3000)

            state.value = state.value.copy(email = email, password = password)
            state.value = state.value.copy(displayProgressBar = false)
            state.value = state.value.copy(successLogin = true)
        }
    }

    fun hideErrorDialog() {
        state.value = state.value.copy(
            errorMessage = null
        )
    }

}