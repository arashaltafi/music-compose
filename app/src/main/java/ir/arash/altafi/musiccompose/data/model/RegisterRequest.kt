package ir.arash.altafi.musiccompose.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("family")
    val family: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
) : Parcelable