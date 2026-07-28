package com.elhady.movies.core.network.api

import com.elhady.movies.core.common.UserDataProvider
import com.elhady.movies.core.network.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val userDataProvider: UserDataProvider,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val language = Locale.getDefault().language
        val sessionId = userDataProvider.sessionId

        val request = chain.request()
        val url: HttpUrl = request.url.newBuilder()
            .addQueryParameter(API_KEY, BuildConfig.API_KEY)
            .addQueryParameter(SESSION_ID, sessionId)
            .addQueryParameter(LANGUAGE, language)
            .build()

        return chain.proceed(request.newBuilder().url(url).build())
    }

    private companion object {
        const val API_KEY = "api_key"
        const val SESSION_ID = "session_id"
        const val LANGUAGE = "language"
    }
}
