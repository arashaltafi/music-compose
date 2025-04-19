package ir.arash.altafi.musiccompose.utils.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import retrofit2.Response

abstract class BaseViewModel : ViewModel() {
    // A shared flow to emit error messages (could be used for showing toasts, etc.)
    protected val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error.asSharedFlow()

    protected fun handleError(e: Throwable) {
        viewModelScope.launch {
            _error.emit(e.localizedMessage ?: "An error occurred")
        }
    }

    fun <T> callApi(
        networkCall: Flow<Response<T>>,
        liveResult: MutableLiveData<T>? = null,
        liveError:  MutableLiveData<Boolean>? = null,
        liveLoading:  MutableLiveData<Boolean>? = null,
        onResponse: ((T) -> Unit)? = null,
    ) {
        viewModelScope.launch {
            networkCall.onStart {
                liveLoading?.value = true
                liveError?.value = false
            }.catch {
                liveLoading?.value = false
                liveError?.value = true
            }.collect { response ->
                if (response.isSuccessful) {
                    response.body()?.let {
                        liveResult?.value = it
                        onResponse?.invoke(it)
                    }
                    liveLoading?.value = false
                    liveError?.value = false
                } else {
                    liveLoading?.value = false
                    liveError?.value = true
                }
            }
        }
    }

    fun <T> callCache(
        cacheCall: Flow<T>,
        liveResult: MutableLiveData<T>? = null,
        liveError: MutableLiveData<Boolean>? = null,
        liveLoading: MutableLiveData<Boolean>? = null,
        onResponse: ((T) -> Unit)? = null,
    ) {
        viewModelScope.launch {
            cacheCall.onStart {
                liveLoading?.value = true
                liveError?.value = false
            }.catch {
                liveLoading?.value = false
                liveError?.value = true
            }.collect { response ->
                liveResult?.value = response
                onResponse?.invoke(response)
                liveLoading?.value = false
                liveError?.value = false
            }
        }
    }

    fun <T> callDatabase(
        databaseCall: Flow<T>,
        liveResult: MutableLiveData<T>? = null,
        liveError:  MutableLiveData<Boolean>? = null,
        liveLoading:  MutableLiveData<Boolean>? = null,
        onResponse: ((T) -> Unit)? = null,
    ) {
        viewModelScope.launch {
            databaseCall.onStart {
                liveLoading?.value = true
                liveError?.value = false
            }.catch {
                liveLoading?.value = false
                liveError?.value = true
            }.collect { response ->
                liveResult?.value = response
                onResponse?.invoke(response)
                liveLoading?.value = false
                liveError?.value = false
            }
        }
    }
}