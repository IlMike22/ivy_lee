package de.mindmarket.ivyleemaster.util.domain

sealed interface DataError : Error {
    enum class Network : DataError {
        BAD_REQUEST,
        SERVER_ERROR,
        SERIALIZATION,
        UNAUTHORIZED,
        REQUEST_TIMEOUT,
        NO_INTERNET
    }
}