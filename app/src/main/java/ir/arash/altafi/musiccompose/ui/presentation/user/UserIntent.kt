package ir.arash.altafi.musiccompose.ui.presentation.user

sealed class UserIntent {
    data class FetchUsers(val pageNumber: Int, val pageSize: Int) : UserIntent()
}