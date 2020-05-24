package ru.rsprm.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import ru.home.collaborativeeducation.network.exceptions.NoConnectivityException

import java.io.IOException

class ConnectionInterceptor(private val context: Context, networkUtils: NetworkUtils) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (!NetworkUtils().isConnected(context)) {
            throw NoConnectivityException()
        } else {
            chain.proceed(chain.request())
        }
    }
}
