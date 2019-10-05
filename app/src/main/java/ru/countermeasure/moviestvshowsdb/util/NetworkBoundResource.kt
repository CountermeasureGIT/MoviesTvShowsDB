package ru.countermeasure.moviestvshowsdb.util

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

abstract class NetworkBoundResource<ResultType, RequestType>(
    override val coroutineContext: CoroutineContext
    //,
    //private val errorHandler: ErrorHandler
) : CoroutineScope {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)

        launch {
            val dbSource = loadFromDb()
            withContext(Dispatchers.Main) {
                result.addSource(dbSource) {
                    result.removeSource(dbSource)
                    fetchFromNetwork(this@launch, dbSource)
                }
            }
        }
    }

    private fun fetchFromNetwork(scope: CoroutineScope, dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        println("test")

        result.addSource(dbSource) { newData ->
            setValue(scope, Resource.loading(newData))
        }

        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)

            when (response) {
                is ApiSuccessResponse -> {
                    launch {
                        saveCallResult(processResponse(response))
                    }
                    result.addSource(loadFromDb()) { newData ->
                        setValue(scope, Resource.success(newData))
                    }

                    Log.d(
                        "MY_LOG_TAG " + this.javaClass.simpleName,
                        "ApiResponse " + (response.body)
                    )
                }
                is ApiEmptyResponse -> {
                    result.addSource(loadFromDb()) { newData ->
                        setValue(scope, Resource.success(newData))
                    }
                    Log.d("MY_LOG_TAG " + this.javaClass.simpleName, "ApiEmptyResponse")
                }
                is ApiErrorResponse -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        setValue(scope, Resource.error(response.errorMessage, newData))
                    }
                    Log.d(
                        "MY_LOG_TAG " + this.javaClass.simpleName,
                        "ApiResponse error " + (response.errorMessage)
                    )
                }
            }
        }
    }

    private fun setValue(scope: CoroutineScope, newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private suspend fun replaceSource(
        oldSource: LiveData<ResultType>,
        newSource: LiveData<ResultType>,
        onChanged: Observer<ResultType>
    ) {
        withContext(Dispatchers.Main) {
            try {
                result.removeSource(oldSource)
                result.addSource(newSource, onChanged)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    protected open fun onFetchFailed() {
        Log.d("MY_LOG_TAG" + this.javaClass.simpleName, "Network error")
    }

    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    protected abstract fun saveCallResult(item: RequestType)

    protected abstract fun loadFromDb(): LiveData<ResultType>

    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}
