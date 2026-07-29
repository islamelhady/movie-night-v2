package com.elhady.movies.core.network.di

import com.elhady.movies.core.network.BuildConfig
import com.elhady.movies.core.network.api.AccountApiService
import com.elhady.movies.core.network.api.AuthApiService
import com.elhady.movies.core.network.interceptor.AuthInterceptor
import com.elhady.movies.core.network.api.GenreApiService
import com.elhady.movies.core.network.api.MovieApiService
import com.elhady.movies.core.network.api.PeopleApiService
import com.elhady.movies.core.network.api.SearchApiService
import com.elhady.movies.core.network.api.TvShowApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieApiService(retrofit: Retrofit): MovieApiService {
        return retrofit.create(MovieApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideTvShowApiService(retrofit: Retrofit): TvShowApiService {
        return retrofit.create(TvShowApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSearchApiService(retrofit: Retrofit): SearchApiService {
        return retrofit.create(SearchApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAccountApiService(retrofit: Retrofit): AccountApiService {
        return retrofit.create(AccountApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePeopleApiService(retrofit: Retrofit): PeopleApiService {
        return retrofit.create(PeopleApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideGenreApiService(retrofit: Retrofit): GenreApiService {
        return retrofit.create(GenreApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideClient(
        authInterceptor: AuthInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }
}
