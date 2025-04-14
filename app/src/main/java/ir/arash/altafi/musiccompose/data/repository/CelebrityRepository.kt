package ir.arash.altafi.musiccompose.data.repository

import ir.arash.altafi.musiccompose.data.api.CelebrityService
import ir.arash.altafi.musiccompose.utils.base.BaseRepository
import javax.inject.Inject

class CelebrityRepository @Inject constructor(
    private val service: CelebrityService,
) : BaseRepository() {

    fun getCelebrities(pageNumber: Int, pageSize: Int) = callApi {
        service.getCelebrities(pageNumber, pageSize)
    }

}