package com.elhady.remote.serviece

import com.elhady.local.AppConfiguration
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val appConfiguration: AppConfiguration
) : Interceptor {

    private val apiKey = "282157b63b2a2ef81abaca304a648cba"

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