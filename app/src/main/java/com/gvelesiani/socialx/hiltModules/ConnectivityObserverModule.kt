package com.gvelesiani.socialx.hiltModules

import com.gvelesiani.socialx.presentation.NetworkStatusTracker
import com.gvelesiani.socialx.presentation.NetworkStatusTrackerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ConnectivityObserverModule {
    @Binds
    abstract fun bindConnectivityObserver(connectivityObserver: NetworkStatusTrackerImpl): NetworkStatusTracker
}