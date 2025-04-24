package ir.arash.altafi.musiccompose.data.api

import ir.arash.altafi.musiccompose.data.model.LoginRequest
import ir.arash.altafi.musiccompose.data.model.LoginResponse
import ir.arash.altafi.musiccompose.data.model.LogoutResponse
import ir.arash.altafi.musiccompose.data.model.RegisterRequest
import ir.arash.altafi.musiccompose.data.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("api/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("api/auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @POST("api/auth/logout")
    suspend fun logout(
        @Header("Authorization") bearerToken: String
    ): Response<LogoutResponse>
}
