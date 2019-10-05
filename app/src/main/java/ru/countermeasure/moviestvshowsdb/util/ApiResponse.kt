package ru.countermeasure.moviestvshowsdb.util

import android.util.Log
import retrofit2.Response
import java.util.regex.Pattern

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


sealed class ApiResponse<T> {
    companion object {
        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(error.message ?: "unknown error")
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(
                        body = body
                    )
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                ApiErrorResponse(errorMsg ?: "unknown error")
            }
        }
    }
}

/**
 * separate class for HTTP 204 responses so that we can make ApiSuccessResponse's body non-null.
 */
class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiSuccessResponse<T>(
    val body: T
) : ApiResponse<T>()

data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()
