package ir.arash.altafi.musiccompose.ui.presentation.celebrity

import ir.arash.altafi.musiccompose.data.model.CelebrityResponse

sealed class CelebrityState {
    object Idle : CelebrityState()
    object Loading : CelebrityState()
    data class Success(val celebrities: List<CelebrityResponse>) : CelebrityState()
    data class Error(val message: String) : CelebrityState()
}