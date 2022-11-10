package com.gvelesiani.socialx.hiltModules

import com.gvelesiani.socialx.api.NetworkApi
import com.gvelesiani.socialx.api.response.NetworkResponseAdapterFactory
import com.gvelesiani.socialx.domain.repositories.AuthTokenRepository
import com.gvelesiani.socialx.interceptors.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://x8ki-letl-twmt.n7.xano.io/api:socialx/")
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(authInterceptor)
            .connectTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .build()
    }

    @Provides
    fun provideInterceptor(
        authTokenRepository: AuthTokenRepository
    ): AuthInterceptor {
        return AuthInterceptor(authTokenRepository)
    }

    @Provides
    fun provideNetworkApi(retrofit: Retrofit): NetworkApi = retrofit.create(NetworkApi::class.java)
}