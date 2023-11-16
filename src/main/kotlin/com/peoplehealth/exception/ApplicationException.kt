package com.peoplehealth.exception

enum class Error {
    INVALID_USER,
    MALFORMED_INPUT,
    UNAUTHORIZED,
    UNKNOWN
}

class ApplicationException(val error: Error, override val cause: Throwable? = null): Exception(cause)