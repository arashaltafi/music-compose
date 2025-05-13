package ir.arash.altafi.musiccompose.ui.presentation.auth

sealed class AuthIntent {
    data class Login(val email: String, val password: String) : AuthIntent()

    data class Register(
        val name: String,
        val family: String,
        val email: String,
        val password: String
    ) : AuthIntent()

    object Logout : AuthIntent()
}