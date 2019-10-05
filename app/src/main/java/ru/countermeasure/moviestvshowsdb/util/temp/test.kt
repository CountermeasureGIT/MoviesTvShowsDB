package ru.countermeasure.moviestvshowsdb.util.temp

import androidx.lifecycle.LiveData
import okhttp3.Request
import retrofit2.*
import ru.countermeasure.moviestvshowsdb.util.ApiResponse
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

//sealed class ApiResponse<out T> {
//    data class Success<T>(val data: T) : ApiResponse<T>()
//    data class Failure(val statusCode: Int?, val message: String? = null) : ApiResponse<Nothing>()
//    object NetworkError : ApiResponse<Nothing>()
//}
//
//abstract class CallDelegate<TIn, TOut>(
//    protected val proxy: Call<TIn>
//) : Call<TOut> {
//    override fun execute(): Response<TOut> = throw NotImplementedError()
//    final override fun enqueue(callback: Callback<TOut>) = enqueueImpl(callback)
//    final override fun clone(): Call<TOut> = cloneImpl()
//
//    override fun cancel() = proxy.cancel()
//    override fun request(): Request = proxy.request()
//    override fun isExecuted() = proxy.isExecuted
//    override fun isCanceled() = proxy.isCanceled
//
//    abstract fun enqueueImpl(callback: Callback<TOut>)
//    abstract fun cloneImpl(): Call<TOut>
//}
//
//class ResultCall<T>(proxy: Call<T>) : CallDelegate<T, ApiResponse<T>>(proxy) {
//    override fun enqueueImpl(callback: Callback<ApiResponse<T>>) =
//        proxy.enqueue(object : Callback<T> {
//            override fun onResponse(call: Call<T>, response: Response<T>) {
//                val code = response.code()
//                val result = if (code in 200 until 300) {
//                    val body = response.body()
//                    if (body != null) {
//                        ApiResponse.Success(body)
//                    } else {
//                        ApiResponse.Failure(null, "Empty response body")
//                    }
//                } else {
//                    ApiResponse.Failure(code)
//                }
//
//                callback.onResponse(this@ResultCall, Response.success(result))
//            }
//
//            override fun onFailure(call: Call<T>, t: Throwable) {
//                val result = if (t is IOException) {
//                    ApiResponse.NetworkError
//                } else {
//                    ApiResponse.Failure(null)
//                }
//
//                callback.onResponse(this@ResultCall, Response.success(result))
//            }
//        })
//
//    override fun cloneImpl() = ResultCall(proxy.clone())
//}
//
//class ResultAdapter<T>(
//    private val clazz: Class<T>
//) : CallAdapter<T, Call<ApiResponse<T>>> {
//    override fun responseType() = clazz
//    override fun adapt(call: Call<T>): Call<ApiResponse<T>> = ResultCall(call)
//}
//
//class MyCallAdapterFactory : CallAdapter.Factory() {
//    override fun get(
//        returnType: Type,
//        annotations: Array<Annotation>,
//        retrofit: Retrofit
//    ) = when (getRawType(returnType)) {
//        Call::class.java -> {
//            val callType = getParameterUpperBound(0, returnType as ParameterizedType)
//            when (getRawType(callType)) {
//                ApiResponse::class.java -> {
//                    val resultType = getParameterUpperBound(0, callType as ParameterizedType)
//                    ResultAdapter(getRawType(resultType))
//                }
//                else -> null
//            }
//        }
//        else -> null
//    }
//}

class LiveDataCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, LiveData<ApiResponse<R>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<R>): LiveData<ApiResponse<R>> {
        return object : LiveData<ApiResponse<R>>() {
            private var started = AtomicBoolean(false)
            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : Callback<R> {
                        override fun onResponse(call: Call<R>, response: Response<R>) {
                            postValue(ApiResponse.create(response))
                        }

                        override fun onFailure(call: Call<R>, throwable: Throwable) {
                            postValue(ApiResponse.create(throwable))
                        }
                    })
                }
            }
        }
    }
}



class LiveDataCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (CallAdapter.Factory.getRawType(returnType) != LiveData::class.java) {
            return null
        }
        val observableType = CallAdapter.Factory.getParameterUpperBound(0, returnType as ParameterizedType)
        val rawObservableType = CallAdapter.Factory.getRawType(observableType)
        if (rawObservableType != ApiResponse::class.java) {
            throw IllegalArgumentException("type must be a resource")
        }
        if (observableType !is ParameterizedType) {
            throw IllegalArgumentException("resource must be parameterized")
        }
        val bodyType = CallAdapter.Factory.getParameterUpperBound(0, observableType)
        return LiveDataCallAdapter<Any>(bodyType)
    }
}
