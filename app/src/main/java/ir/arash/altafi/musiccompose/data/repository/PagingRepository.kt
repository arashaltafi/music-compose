package ir.arash.altafi.musiccompose.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import ir.arash.altafi.musiccompose.utils.base.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PagingRepository @Inject constructor(
    private val service: PagingService,
) : BaseRepository() {

    fun getUsersPagingData(pageSize: Int): Flow<PagingData<UserResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UsersPagingSource(service, pageSize) }
        ).flow
    }

}