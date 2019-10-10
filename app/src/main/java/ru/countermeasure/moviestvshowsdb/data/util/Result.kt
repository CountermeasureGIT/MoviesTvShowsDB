package ru.countermeasure.moviestvshowsdb.data.util

sealed class Result<out T> {
    data class error<T>(val message: String, val cachedData: T? = null) : Result<T>()
    data class loading<T>(val cachedData: T? = null) : Result<T>()
    data class success<T>(val data: T) : Result<T>()
}