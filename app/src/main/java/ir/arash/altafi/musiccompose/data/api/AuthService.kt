package ir.arash.altafi.musiccompose.data.api

import ir.arash.altafi.musiccompose.data.model.CelebrityResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {

    @POST("test_paging/test_paging2.php")
    suspend fun sendLogin(
//        @Body loginRequest: LoginRequest // fix here
    ): Response<List<CelebrityResponse>>

}