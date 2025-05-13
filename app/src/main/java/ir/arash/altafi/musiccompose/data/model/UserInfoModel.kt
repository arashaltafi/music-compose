package ir.arash.altafi.musiccompose.data.model

import com.google.gson.annotations.SerializedName

data class UserInfoModel(
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("family")
    val family: String? = null,

    @SerializedName("avatar")
    val avatar: String? = null,

    @SerializedName("token")
    val token: String? = null,

    @SerializedName("firebaseToken")
    val firebaseToken: String? = null,
)