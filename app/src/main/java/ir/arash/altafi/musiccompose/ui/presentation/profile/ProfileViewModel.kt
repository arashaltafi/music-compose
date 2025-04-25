package ir.arash.altafi.musiccompose.ui.presentation.profile

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.arash.altafi.musiccompose.data.model.UserInfoModel
import ir.arash.altafi.musiccompose.data.repository.AuthRepository
import ir.arash.altafi.musiccompose.data.repository.DataStoreRepository
import ir.arash.altafi.musiccompose.data.repository.ProfileRepository
import ir.arash.altafi.musiccompose.ui.base.ApiState
import ir.arash.altafi.musiccompose.ui.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataStore: DataStoreRepository,
    private val profileRepository: ProfileRepository,
    private val authRepository: AuthRepository
) : BaseViewModel<Any>() {

    fun onEvent(event: ProfileIntent) {
        when (event) {
            is ProfileIntent.Info -> performUpdateInfo(event.name, event.family, event.password)
            is ProfileIntent.Avatar -> performUpdateAvatar(event.avatar)
            ProfileIntent.Logout -> performLogout()
        }
    }

    private fun performUpdateAvatar(avatar: String) {
        launchApi(
            dataStore = dataStore,
            apiCall = { profileRepository.updateAvatar(avatar) }
        ) { data ->
            viewModelScope.launch {
                dataStore.setUserInfo(
                    UserInfoModel(
                        name = data.name,
                        family = data.family,
                        avatar = data.avatar,
                        token = data.token,
                        firebaseToken = data.firebaseToken,
                    )
                )
                delay(100)
                getUserInfoCache()
            }
        }
    }

    private fun performUpdateInfo(name: String, family: String, password: String) {
        launchApi(
            dataStore = dataStore,
            apiCall = { profileRepository.updateInfoRequest(name, family, password) }
        ) { data ->
            viewModelScope.launch {
                dataStore.setUserInfo(
                    UserInfoModel(
                        name = data.name,
                        family = data.family,
                        avatar = data.avatar,
                        token = data.token,
                        firebaseToken = data.firebaseToken,
                    )
                )
                delay(100)
                getUserInfoCache()
            }
        }
    }

    private fun getUserInfoCache() {
        viewModelScope.launch {
            dataStore.getUserInfo().collect {
                _apiState.value = ApiState.Success(it)
            }
        }
    }

    private fun performLogout() {
        launchApi(
            dataStore = dataStore,
            apiCall = { authRepository.logoutRequest() }
        ) {
            viewModelScope.launch {
                dataStore.setToken("")
                _apiState.value = ApiState.Success("Logout successful")
            }
        }
    }
}