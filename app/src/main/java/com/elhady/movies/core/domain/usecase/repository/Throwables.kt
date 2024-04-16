package com.elhady.movies.core.domain.usecase.repository

open class ApiThrowable(message: String?): Throwable(message)
class UnauthorizedThrowable: ApiThrowable("Unauthorized")
class NoNetworkThrowable: ApiThrowable("noNetwork")
class TimeoutThrowable: ApiThrowable("timeout")
class ParsingThrowable: ApiThrowable("Parsing Error")
class ServerErrorThrowable: ApiThrowable("Server Error")
class ForbiddenThrowable: ApiThrowable("Refuses to authorize it")
