package ir.arash.altafi.musiccompose.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.arash.altafi.musiccompose.data.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import retrofit2.Response

abstract class BaseViewModel<T> : ViewModel() {

    protected val _apiState = MutableStateFlow<ApiState<T>>(ApiState.Loading)
    val apiState: StateFlow<ApiState<T>> = _apiState

    /**
     * Centralized launcher for any Retrofit call returning Response<R>
     */
    protected fun <R> launchApi(
        dataStore: DataStoreRepository,
        apiCall: suspend () -> Response<R>,
        onSuccessData: (R) -> Unit
    ) {
        viewModelScope.launch {
            _apiState.value = ApiState.Loading
            try {
                val response = apiCall()
                when (response.code()) {
                    in 200..299 -> {
                        val body = response.body()
                        if (body != null) {
                            onSuccessData(body)
                        } else {
                            val raw = response.errorBody()?.string().orEmpty()
                            val msg = """"message"\s*:\s*"([^"]+)"""".toRegex()
                                .find(raw)
                                ?.groupValues
                                ?.get(1)
                                ?: raw.ifBlank { "Server error ${response.code()}" }
                            _apiState.value = ApiState.Error(msg)
                        }
                    }
                    400, 500 -> {
                        val raw = response.errorBody()?.string().orEmpty()
                        val msg = """"message"\s*:\s*"([^"]+)"""".toRegex()
                            .find(raw)
                            ?.groupValues
                            ?.get(1)
                            ?: raw.ifBlank { "Server error ${response.code()}" }
                        _apiState.value = ApiState.Error(msg)
                    }
                    401 -> {
                        dataStore.setToken("")
                        _apiState.value = ApiState.Unauthorized
                    }
                    403 -> {
                        _apiState.value = ApiState.Error("you don't access!")
                    }
                    else -> {
                        _apiState.value = ApiState.Error("Unexpected HTTP ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                _apiState.value = ApiState.Error(e.localizedMessage ?: "Unknown Error")
            }
        }
    }

    fun <T> callCache(
        cacheCall: Flow<T>,
        liveResult: MutableLiveData<T>? = null,
        onResponse: ((T) -> Unit)? = null,
    ) {
        viewModelScope.launch {
            cacheCall.onStart {
            }.catch {
            }.collect { response ->
                liveResult?.value = response
                onResponse?.invoke(response)
            }
        }
    }
}