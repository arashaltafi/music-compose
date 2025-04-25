package ir.arash.altafi.musiccompose.ui.presentation.profile

sealed class ProfileIntent {
    data class Info(
        val name: String,
        val family: String,
        val password: String,
    ) : ProfileIntent()

    data class Avatar(
        val avatar: String,
    ) : ProfileIntent()

    object Logout : ProfileIntent()
}