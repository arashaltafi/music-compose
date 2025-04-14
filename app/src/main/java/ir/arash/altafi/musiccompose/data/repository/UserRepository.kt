package ir.arash.altafi.musiccompose.data.repository

import ir.arash.altafi.musiccompose.data.api.UserService
import ir.arash.altafi.musiccompose.utils.base.BaseRepository
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val service: UserService,
) : BaseRepository() {

    fun getUsers(pageNumber: Int, pageSize: Int) = callApi {
        service.getUsersPaging(pageNumber, pageSize)
    }

}

