package com.elhady.movies.data.remote

import com.elhady.movies.data.local.AppConfiguration
import com.elhady.movies.utilities.Constants
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val appConfiguration: AppConfiguration
) : Interceptor {

    private val apiKey = Constants.API_KEY

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val httpUrl = request.url.newBuilder().addQueryParameter(API_KEY_PARAMETER, apiKey)
            .addQueryParameter(SESSION_ID_KEY, appConfiguration.getSessionId()).build()
        request = request.newBuilder().url(httpUrl).build()
        return chain.proceed(request)
    }

    companion object {
        private const val API_KEY_PARAMETER = "api_key"
        private const val SESSION_ID_KEY = "session_id"
    }
}