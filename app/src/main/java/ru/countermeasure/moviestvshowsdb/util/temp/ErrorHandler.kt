package ru.countermeasure.moviestvshowsdb.util.temp

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException

class ErrorHandler(
    private val gson: Gson,
    private val stringResource: StringResource
) {
    suspend fun proceed(error: Throwable): String = when (error) {
        is HttpException -> {
            error.userMessage(stringResource)
        }
        is ServerError -> {
            try {
                val errorBody = gson.fromJson(error.errorBody, ErrorResponse::class.java)
                errorBody?.status_message
            } catch (e: JsonSyntaxException) {
                error.userMessage(stringResource)
            } ?: error.userMessage(stringResource)
        }
        else -> error.userMessage(stringResource)
    }
}