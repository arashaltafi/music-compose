package ir.arash.altafi.musiccompose.ui.presentation.testDetail

import androidx.lifecycle.viewModelScope
import ir.arash.altafi.musiccompose.data.model.TestDetailEntity
import ir.arash.altafi.musiccompose.data.repository.TestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.arash.altafi.musiccompose.ui.base.ApiState
import ir.arash.altafi.musiccompose.utils.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestDetailViewModel @Inject constructor(
    private val repository: TestRepository
) : BaseViewModel<TestDetailEntity>() {

    fun loadUserDetail(id: String) {
        _apiState.value = ApiState.Loading
        viewModelScope.launch {
            try {
                val user = repository.getUserDetail(id)
                if (user != null) {
                    _apiState.value = ApiState.Success(user)
                } else {
                    _apiState.value = ApiState.Error("User not found")
                }
            } catch (e: Exception) {
                _apiState.value = ApiState.Error(e.localizedMessage ?: "Error loading detail")
            }
        }
    }
}