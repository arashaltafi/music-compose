package ir.arash.altafi.musiccompose.data.api

import ir.arash.altafi.musiccompose.data.model.UserResponse
import ir.arash.altafi.musiccompose.utils.base.BaseService
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PagingService: BaseService {

    @GET("test_paging/test_paging.php")
    suspend fun getUsers(
        @Query("page_number") pageNumber: Int,
        @Query("page_size") pageSize: Int
    ): Response<List<UserResponse>>

}