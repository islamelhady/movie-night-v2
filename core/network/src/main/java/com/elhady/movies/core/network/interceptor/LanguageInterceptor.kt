package com.elhady.movies.core.network.interceptor

import com.elhady.movies.core.network.util.NetworkConstants
import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanguageInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val currentLanguage = Locale.getDefault().language

        val originalRequest = chain.request()

        val urlWithLanguage = originalRequest.url.newBuilder()
            .setQueryParameter(NetworkConstants.LANGUAGE_QUERY, currentLanguage)
            .build()

        val newRequest = originalRequest.newBuilder().url(urlWithLanguage).build()
        return chain.proceed(newRequest)
    }
}