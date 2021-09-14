package dev.leonardom.loginjetpackcompose.presentation.login

import androidx.annotation.StringRes

data class LoginState(
    val email: String = "",
    val password: String = "",
    val successLogin: Boolean = false,
    val displayProgressBar: Boolean = false,
    @StringRes val errorMessage: Int? = null
)
