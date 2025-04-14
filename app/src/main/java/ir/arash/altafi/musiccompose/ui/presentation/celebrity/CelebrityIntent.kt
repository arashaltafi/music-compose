package ir.arash.altafi.musiccompose.ui.presentation.celebrity

sealed class CelebrityIntent {
    data class FetchCelebrities(val pageNumber: Int, val pageSize: Int) : CelebrityIntent()
}