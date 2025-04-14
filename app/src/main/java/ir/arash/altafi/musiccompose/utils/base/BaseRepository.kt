package ir.arash.altafi.musiccompose.utils.base

import ir.arash.altafi.musiccompose.utils.ext.flowIO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

abstract class BaseRepository {
    fun <T> callApi(networkCall: suspend () -> Response<T>) = flowIO {
        val response = networkCall()
        emit(response)
    }

    fun <T> callCache(cacheCall: suspend () -> T) = flow {
        val response = cacheCall()
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun <T> callDatabase(databaseCall: suspend () -> T) = flow {
        val response = databaseCall()
        emit(response)
    }.flowOn(Dispatchers.IO)
}