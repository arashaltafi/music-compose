package ir.arash.altafi.musiccompose.ui.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.arash.altafi.musiccompose.data.repository.DataStoreRepository
import ir.arash.altafi.musiccompose.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: DataStoreRepository
) : BaseViewModel<Any>() {
    private val _cachedToken = MutableLiveData<String>()
    val cachedToken: LiveData<String>
        get() = _cachedToken

    fun getToken() = callCache(
        cacheCall = repository.getToken(),
        liveResult = _cachedToken,
    )

}