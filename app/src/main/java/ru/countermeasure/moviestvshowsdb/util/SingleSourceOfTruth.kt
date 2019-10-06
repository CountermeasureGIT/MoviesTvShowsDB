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
        emit(Result.loading())

        val invokeResult: LiveData<ResultType> = databaseQuery.invoke()
        val dbSuccessResult: LiveData<Result<ResultType>> = invokeResult.map { Result.success(it) }

        emitSource(dbSuccessResult)

        val response = networkCall.invoke()
        if (response is Result.success) {
            saveCallResult(response.data!!)
        } else if (response is Result.error) {
            emitSource(invokeResult.map {
                Result.error(response.message, it)
            })
        }
    }