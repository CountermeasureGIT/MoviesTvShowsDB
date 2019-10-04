package ru.countermeasure.moviestvshowsdb.util

import retrofit2.Response
import ru.countermeasure.moviestvshowsdb.util.temp.ServerError

//sealed class ApiResponse<T> {
//    companion object {
//        fun <T> create(error: Throwable): ApiErrorResponse<T> =
//            ApiErrorResponse(error)
//
//        fun <T> create(response: Response<T>): ApiResponse<T> =
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    ApiSuccessResponse(it)
//                } ?: ApiEmptyResponse()
//            } else {
//                val msg = response.errorBody()?.string()
//                ApiErrorResponse(
//                    ServerError(
//                        response.code(),
//                        msg
//                    )
//                )
//            }
//    }
//}
//
//data class ApiErrorResponse<T>(val error: Throwable) : ApiResponse<T>()
//
//data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()
//
//class ApiEmptyResponse<T> : ApiResponse<T>()