package ru.countermeasure.moviestvshowsdb.util

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.*
import ru.countermeasure.moviestvshowsdb.util.temp.ApiResponse
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
            result.addSource(dbSource) {
                result.removeSource(dbSource)
                fetchFromNetwork(dbSource)
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        launch {
            val apiResponse = createCall()
            println("test")

            withContext(Dispatchers.Main) {
                result.addSource(dbSource) { newData ->
                    setValue(this, Resource.loading(newData))
                }
            }

            when (apiResponse) {
                is ApiResponse.Success -> {
                    saveCallResult(processResponse(apiResponse))
                    withContext(Dispatchers.Main) {
                        result.removeSource(dbSource)
                        result.addSource(loadFromDb()) { newData ->
                            setValue(this, Resource.success(newData))
                        }
                    }
//                    replaceSource(dbSource, dbSource, Observer {
//                        result.value = Resource.success(it)
//                    })
                    Log.d(
                        "MY_LOG_TAG " + this.javaClass.simpleName,
                        "ApiResponse " + (apiResponse.data)
                    )
                }
                is ApiResponse.Failure -> {
                    onFetchFailed()
                    withContext(Dispatchers.Main) {
                        result.removeSource(dbSource)
                        result.addSource(dbSource) { newData ->
                            setValue(this, Resource.error(apiResponse.message ?: "", newData))
                        }
                    }
//                    replaceSource(dbSource, dbSource, Observer {
//                        result.value = Resource.error(apiResponse.message ?: "Error", it)
//                    })
                    Log.d(
                        "MY_LOG_TAG " + this.javaClass.simpleName,
                        "ApiResponse error " + (apiResponse.message)
                    )
                }
                is ApiResponse.NetworkError -> {
                    onFetchFailed()
                    withContext(Dispatchers.Main) {
                        result.removeSource(dbSource)
                        result.addSource(dbSource) { newData ->
                            setValue(this, Resource.error("Network Error", newData))
                        }
                    }
//                    replaceSource(dbSource, dbSource, Observer {
//                        result.value = Resource.error("Network error", it)
//                    })
                    Log.d("MY_LOG_TAG " + this.javaClass.simpleName, "ApiResponse network error")
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

    protected open fun processResponse(response: ApiResponse.Success<RequestType>) = response.data

    protected abstract suspend fun saveCallResult(item: RequestType)

    protected abstract suspend fun loadFromDb(): LiveData<ResultType>

    protected abstract suspend fun createCall(): ApiResponse<RequestType>
}
