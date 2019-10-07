package ru.countermeasure.moviestvshowsdb.model.util

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
        val dbLoadingResult: LiveData<Result<ResultType>> = invokeResult.map {
            Result.loading(it)
        }
        val dbSuccessResult: LiveData<Result<ResultType>> = invokeResult.map {
            Result.success(it)
        }

        emitSource(dbLoadingResult)

        //TODO убрать задержку запроса в сеть
        delay(1000)

        val response = networkCall.invoke()
        if (response is Result.success) {
            saveCallResult(response.data!!)
            emitSource(dbSuccessResult)
        } else if (response is Result.error) {
            emitSource(invokeResult.map {
                Result.error(response.message, it)
            })
        }
    }