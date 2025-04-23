package ir.arash.altafi.musiccompose.data.repository

import ir.arash.altafi.musiccompose.data.api.AuthService
import ir.arash.altafi.musiccompose.data.model.LoginRequest
import ir.arash.altafi.musiccompose.data.model.LoginResponse
import ir.arash.altafi.musiccompose.data.model.LogoutResponse
import ir.arash.altafi.musiccompose.data.model.RegisterRequest
import ir.arash.altafi.musiccompose.data.model.RegisterResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authService: AuthService
) {

    suspend fun loginRequest(email: String, password: String): Response<LoginResponse> {
        return authService.login(LoginRequest(email, password))
    }

    suspend fun registerRequest(
        name: String,
        family: String,
        email: String,
        password: String
    ): Response<RegisterResponse> {
        return authService.register(RegisterRequest(name, family, email, password))
    }

    suspend fun logoutRequest(token: String): Response<LogoutResponse> {
        return authService.logout("Bearer $token")
    }
}