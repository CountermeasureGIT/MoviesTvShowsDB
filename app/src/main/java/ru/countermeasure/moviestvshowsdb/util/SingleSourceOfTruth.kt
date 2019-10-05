package ru.countermeasure.moviestvshowsdb.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.*

fun <ResultType, RequestType> resultLiveData(
    databaseQuery: () -> LiveData<ResultType>,
    networkCall: suspend () -> Result<RequestType>,
    saveCallResult: suspend (RequestType) -> Unit
): LiveData<Result<ResultType>> =
    liveData(Dispatchers.IO) {
        emit(Result.loading(null))

        val dbSource: LiveData<Result<ResultType>> = databaseQuery.invoke().map {
            Result.success(it)
        }

        emitSource(dbSource)

        val response = networkCall.invoke()
        if (response.status == Result.Status.SUCCESS) {
            saveCallResult(response.data!!)
        } else if (response.status == Result.Status.ERROR) {
            emit(Result.error(response.message!!))
            emitSource(dbSource)
        }
    }