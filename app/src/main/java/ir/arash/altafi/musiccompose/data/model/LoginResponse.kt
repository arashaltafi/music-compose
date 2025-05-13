package ir.arash.altafi.musiccompose.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResponse(
    @SerializedName("access_token")
    val accessToken: String,
) : Parcelable