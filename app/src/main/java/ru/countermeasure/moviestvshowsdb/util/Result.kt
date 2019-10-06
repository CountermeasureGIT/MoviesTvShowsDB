package ru.countermeasure.moviestvshowsdb.util

//cachedData class Result<out T>(val status: Status, val cachedData: T?, val message: String?) {
//
//    enum class Status {
//        SUCCESS,
//        ERROR,
//        LOADING
//    }
//
//    companion object {
//        fun <T> success(cachedData: T): Result<T> {
//            return Result(Status.SUCCESS, cachedData, null)
//        }
//
//        fun <T> error(message: String, cachedData: T? = null): Result<T> {
//            return Result(Status.ERROR, cachedData, message)
//        }
//
//        fun <T> loading(cachedData: T? = null): Result<T> {
//            return Result(Status.LOADING, cachedData, null)
//        }
//    }
//}

sealed class Result<out T> {
    data class error<T>(val message: String, val cachedData: T? = null) : Result<T>()
    data class loading<T>(val data: T? = null) : Result<T>()
    data class success<T>(val data: T) : Result<T>()
}