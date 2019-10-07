package ru.countermeasure.moviestvshowsdb.model.util

sealed class Result<out T> {
    data class error<T>(val message: String, val cachedData: T? = null) : Result<T>()
    data class loading<T>(val data: T? = null) : Result<T>()
    data class success<T>(val data: T) : Result<T>()
}