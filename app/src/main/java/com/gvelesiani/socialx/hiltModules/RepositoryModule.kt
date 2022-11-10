package com.gvelesiani.socialx.hiltModules

import com.gvelesiani.socialx.data.repositories.auth.AuthRepositoryImpl
import com.gvelesiani.socialx.data.repositories.auth.AuthTokenRepositoryImpl
import com.gvelesiani.socialx.domain.repositories.AuthRepository
import com.gvelesiani.socialx.domain.repositories.AuthTokenRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAuthTokenRepository(authTokenRepositoryImpl: AuthTokenRepositoryImpl): AuthTokenRepository

    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository
}