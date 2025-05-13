package ir.arash.altafi.musiccompose.data.api

import ir.arash.altafi.musiccompose.data.model.UpdateAvatarRequest
import ir.arash.altafi.musiccompose.data.model.UpdateInfoRequest
import ir.arash.altafi.musiccompose.data.model.UpdateInfoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ProfileService {
    @POST("api/profile/updateInfo")
    suspend fun updateInfo(
        @Body request: UpdateInfoRequest
    ): Response<UpdateInfoResponse>

    @POST("api/profile/updateAvatar")
    suspend fun updateAvatar(
        @Body request: UpdateAvatarRequest
    ): Response<UpdateInfoResponse>
}
