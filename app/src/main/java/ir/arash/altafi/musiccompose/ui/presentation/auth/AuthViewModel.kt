package ir.arash.altafi.musiccompose.ui.presentation.auth

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.arash.altafi.musiccompose.data.repository.AuthRepository
import ir.arash.altafi.musiccompose.data.repository.DataStoreRepository
import ir.arash.altafi.musiccompose.ui.base.ApiState
import ir.arash.altafi.musiccompose.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val dataStore: DataStoreRepository,
    private val authRepository: AuthRepository
) : BaseViewModel<Any>() {

    fun onEvent(event: AuthIntent) {
        when (event) {
            is AuthIntent.Login -> performLogin(event.email, event.password)
            is AuthIntent.Register -> performRegister(event.name, event.family, event.email, event.password)
            AuthIntent.Logout -> performLogout()
        }
    }

    private fun performLogin(email: String, password: String) {
        launchApi(
            dataStore = dataStore,
            apiCall = { authRepository.loginRequest(email, password) }
        ) { data ->
            viewModelScope.launch {
                data.accessToken.let {
                    dataStore.setToken(it)
                    _apiState.value = ApiState.Success("Login successfully")
                }
            }
        }
    }

    private fun performRegister(name: String, family: String, email: String, password: String) {
        launchApi(
            dataStore = dataStore,
            apiCall = { authRepository.registerRequest(name, family, email, password) }
        ) { data ->
            viewModelScope.launch {
                data.accessToken.let {
                    dataStore.setToken(it)
                    _apiState.value = ApiState.Success("Registration successfully")
                }
            }
        }
    }

    private fun performLogout() {
        val token = dataStore.getTokenString()
        launchApi(
            dataStore = dataStore,
            apiCall = { authRepository.logoutRequest(token) }
        ) {
            viewModelScope.launch {
                dataStore.setToken("")
                _apiState.value = ApiState.Success("Logout successful")
            }
        }
    }
}