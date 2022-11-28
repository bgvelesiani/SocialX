package com.gvelesiani.socialx.hiltModules

import com.gvelesiani.socialx.domain.helpers.uriPath.FileHelper
import com.gvelesiani.socialx.domain.helpers.uriPath.FileHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class HelperModule {
    @Binds
    abstract fun bindURIPathHelper(uriPathHelperImpl: FileHelperImpl): FileHelper
}