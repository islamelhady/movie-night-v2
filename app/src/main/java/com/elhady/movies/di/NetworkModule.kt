package com.elhady.movies.di

import com.elhady.local.Constants
import com.elhady.remote.serviece.AuthInterceptor
import com.elhady.remote.serviece.MovieService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMovieService(
        client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): MovieService {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .build()

        return retrofit.create(MovieService::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

//    @Provides
//    @Singleton
//    fun provideAuthInterceptor(): AuthInterceptor {
//        return AuthInterceptor()
//    }

    @Provides
    @Singleton
    fun provideGson():Gson = Gson()
}