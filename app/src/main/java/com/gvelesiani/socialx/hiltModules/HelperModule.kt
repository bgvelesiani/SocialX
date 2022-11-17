package com.gvelesiani.socialx.hiltModules

import com.gvelesiani.socialx.domain.helpers.uriPath.URIPathHelper
import com.gvelesiani.socialx.domain.helpers.uriPath.URIPathHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class HelperModule {
    @Binds
    abstract fun bindURIPathHelper(uriPathHelperImpl: URIPathHelperImpl): URIPathHelper
}