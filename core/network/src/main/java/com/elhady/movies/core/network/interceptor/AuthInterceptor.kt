package com.elhady.movies.core.network.interceptor

import com.elhady.movies.core.common.UserDataProvider
import com.elhady.movies.core.network.BuildConfig
import com.elhady.movies.core.network.util.NetworkConstants
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val userDataProvider: UserDataProvider,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val sessionId = userDataProvider.sessionId

        val request = chain.request()
        val urlBuilder = request.url.newBuilder()
            .setQueryParameter(NetworkConstants.API_KEY_QUERY, BuildConfig.API_KEY)

        if (!sessionId.isNullOrBlank()) {
            urlBuilder.setQueryParameter(NetworkConstants.SESSION_ID_QUERY, sessionId)
        }

        val url: HttpUrl = urlBuilder.build()

        return chain.proceed(request.newBuilder().url(url).build())
    }
}