package ir.arash.altafi.musiccompose.ui.presentation.auth

import ir.arash.altafi.musiccompose.data.repository.DataStoreRepository
import ir.arash.altafi.musiccompose.utils.JsonUtils
import ir.arash.altafi.musiccompose.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private var jsonUtils: JsonUtils,
) : BaseViewModel() {

//    private val _liveLogin = MutableStateFlow<ReceiveMessage?>(null)
//    val liveLogin: StateFlow<ReceiveMessage?>
//        get() = _liveLogin
//
//    private val _liveLogout = MutableStateFlow<ReceiveMessage?>(null)
//    val liveLogout: StateFlow<ReceiveMessage?>
//        get() = _liveLogout
//
//    private val _liveRegister = MutableStateFlow<ReceiveMessage?>(null)
//    val liveRegister: StateFlow<ReceiveMessage?>
//        get() = _liveRegister

    fun sendLogin(email: String, password: String) {

    }

    fun sendRegister(phone: String, name: String, family: String) {

    }

    fun sendLogout() {
    }
}