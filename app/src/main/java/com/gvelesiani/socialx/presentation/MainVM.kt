package com.gvelesiani.socialx.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvelesiani.socialx.domain.repositories.AuthTokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val authTokenRepository: AuthTokenRepository,
    private val networkStatusTracker: NetworkStatusTracker
) : ViewModel() {
    private val _token: MutableSharedFlow<String> = MutableSharedFlow()
    val token = _token.asSharedFlow()

    init {
        getAuthToken()
    }

    val state =
        networkStatusTracker.getNetworkStatus()
            .map(
                onUnavailable = { Network.Unavailable },
                onAvailable = { Network.Available },
            )

    private fun getAuthToken() {
        viewModelScope.launch {
            _token.emit(authTokenRepository.getToken())
        }
    }



    sealed class Network {
        object Available : Network()
        object Unavailable : Network()
    }
}

// monitoring
sealed class NetworkStatus {
    object Available : NetworkStatus()
    object Unavailable : NetworkStatus()
}

interface NetworkStatusTracker {
    fun getNetworkStatus(): Flow<NetworkStatus>
}
class NetworkStatusTrackerImpl @Inject constructor(@ApplicationContext context: Context): NetworkStatusTracker {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun getNetworkStatus() = callbackFlow {
        val networkStatusCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onUnavailable() {
                launch {
                    send(NetworkStatus.Unavailable)
                }
            }

            override fun onAvailable(network: Network) {
                launch { send(NetworkStatus.Available) }
            }

            override fun onLost(network: Network) {
                launch { send(NetworkStatus.Unavailable) }
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(request, networkStatusCallback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(networkStatusCallback)
        }
    }
}

inline fun <Result> Flow<NetworkStatus>.map(
    crossinline onUnavailable: suspend () -> Result,
    crossinline onAvailable: suspend () -> Result,
): Flow<Result> = map { status ->
    when (status) {
        NetworkStatus.Unavailable -> onUnavailable()
        NetworkStatus.Available -> onAvailable()
    }
}