package ir.arash.altafi.musiccompose.data.repository

import ir.arash.altafi.musiccompose.data.api.ProfileService
import ir.arash.altafi.musiccompose.data.model.UpdateAvatarRequest
import ir.arash.altafi.musiccompose.data.model.UpdateInfoRequest
import ir.arash.altafi.musiccompose.data.model.UpdateInfoResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
    private val profileService: ProfileService
) {

    suspend fun updateAvatar(avatar: String): Response<UpdateInfoResponse> {
        return profileService.updateAvatar(UpdateAvatarRequest(avatar))
    }

    suspend fun updateInfoRequest(
        name: String,
        family: String,
        password: String
    ): Response<UpdateInfoResponse> {
        return profileService.updateInfo(UpdateInfoRequest(name, family, password))
    }
}