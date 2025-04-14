package ir.arash.altafi.musiccompose.ui.presentation.testList

import androidx.lifecycle.viewModelScope
import ir.arash.altafi.musiccompose.data.model.TestDetailEntity
import ir.arash.altafi.musiccompose.data.repository.TestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.arash.altafi.musiccompose.ui.base.ApiState
import ir.arash.altafi.musiccompose.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestListViewModel @Inject constructor(
    private val repository: TestRepository
) : BaseViewModel<List<TestDetailEntity>>() {

    init {
        loadUsers()
    }

    fun loadUsers() {
        _apiState.value = ApiState.Loading
        viewModelScope.launch {
            try {
                val users = repository.getUserList() ?: emptyList()
                _apiState.value = ApiState.Success(users)
            } catch (e: Exception) {
                _apiState.value = ApiState.Error(e.localizedMessage ?: "Error loading users")
            }
        }
    }
}