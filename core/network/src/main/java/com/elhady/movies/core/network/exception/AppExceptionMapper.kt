package com.elhady.movies.core.network.exception

import com.elhady.movies.core.common.AppException
import com.elhady.movies.core.common.mapper.Mapper

interface AppExceptionMapper: Mapper<Exception, AppException> {
    fun map(code: Int, message: String?): AppException
}