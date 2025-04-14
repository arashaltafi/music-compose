package ir.arash.altafi.musiccompose.ui.presentation.user

import ir.arash.altafi.musiccompose.data.model.UserResponse

sealed class UserState {
    object Idle : UserState()
    object Loading : UserState()
    data class Success(val users: List<UserResponse>) : UserState()
    data class Error(val message: String) : UserState()
}