package ir.arash.altafi.musiccompose.data.dataSource

import ir.arash.altafi.musiccompose.data.api.TestService
import ir.arash.altafi.musiccompose.data.model.TestDetailEntity
import javax.inject.Inject

class TestDataSource @Inject constructor(private val apiService: TestService) {

    suspend fun fetchUserList(): List<TestDetailEntity>? {
        val response = apiService.getUsers()
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun fetchUserPaging(pageNumber: Int, pageSize: Int): List<TestDetailEntity>? {
        val response = apiService.getUsersPaging(pageNumber, pageSize)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun fetchUserDetail(id: String): TestDetailEntity? {
        val response = apiService.getUserDetail(id)
        return if (response.isSuccessful) response.body() else null
    }

}