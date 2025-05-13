package ir.arash.altafi.musiccompose.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UpdateAvatarRequest(
    @SerializedName("avatar")
    val avatar: String,
) : Parcelable