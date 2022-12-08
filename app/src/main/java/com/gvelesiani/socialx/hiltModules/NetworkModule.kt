package com.gvelesiani.socialx.hiltModules

import com.gvelesiani.socialx.api.AuthorizationApi
import com.gvelesiani.socialx.api.CommentsApi
import com.gvelesiani.socialx.api.ImagesApi
import com.gvelesiani.socialx.api.PostsApi
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
            .baseUrl("https://zpv1sz.deta.dev/")
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
    fun provideAuthApi(retrofit: Retrofit): AuthorizationApi =
        retrofit.create(AuthorizationApi::class.java)

    @Provides
    fun provideImageApi(retrofit: Retrofit): ImagesApi = retrofit.create(ImagesApi::class.java)

    @Provides
    fun providePostApi(retrofit: Retrofit): PostsApi = retrofit.create(PostsApi::class.java)

    @Provides
    fun provideCommentApi(retrofit: Retrofit): CommentsApi = retrofit.create(CommentsApi::class.java)
}