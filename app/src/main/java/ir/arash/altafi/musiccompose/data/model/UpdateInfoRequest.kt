package ir.arash.altafi.musiccompose.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UpdateInfoRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("family")
    val family: String,
    @SerializedName("password")
    val password: String,
) : Parcelable