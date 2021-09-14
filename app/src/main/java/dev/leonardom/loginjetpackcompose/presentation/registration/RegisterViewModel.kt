package dev.leonardom.loginjetpackcompose.presentation.registration

import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.leonardom.loginjetpackcompose.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {

    val state: MutableState<RegisterState> = mutableStateOf(RegisterState())

    fun register(
        name: String,
        email: String,
        phoneNumber: String,
        password: String,
        confirmPassword: String
    ) {
        val errorMessage = if(name.isBlank() || email.isBlank() || phoneNumber.isBlank() || password.isBlank() || confirmPassword.isBlank()){
            R.string.error_input_empty
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            R.string.error_not_a_valid_email
        } else if(!Patterns.PHONE.matcher(phoneNumber).matches()) {
            R.string.error_not_a_valid_phone_number
        } else if(password != confirmPassword) {
            R.string.error_incorrectly_repeated_password
        } else null

        errorMessage?.let {
            state.value = state.value.copy(errorMessage = errorMessage)
            return
        }

        viewModelScope.launch {
            state.value = state.value.copy(displayProgressBar = true)

            delay(3000)

            state.value = state.value.copy(displayProgressBar = false)
        }
    }

    fun hideErrorDialog() {
        state.value = state.value.copy(
            errorMessage = null
        )
    }

}