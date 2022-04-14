package com.tijani.rememberkotlinskills.di

import android.content.Context
import com.tijani.rememberkotlinskills.core.dataSource.remote.ApiHelper
import com.tijani.rememberkotlinskills.core.dataSource.remote.ApiHelperImpl
import com.tijani.rememberkotlinskills.core.dataSource.remote.ApiService
import com.tijani.rememberkotlinskills.utils.NetworkHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

//private val baseUrl = "http://192.168.43.35/shirzadProject/"


val retrofitModule = module {

    single { provideOkHttpClient() }
    single { provideBaseUrl() }
    single { provideRetrofi(get(), get()) }
    single { provideApiService(get()) }
    single { provideNetworkHelper(androidContext()) }
    single<ApiHelper> {
        return@single ApiHelperImpl(get())
    }

}


private fun provideNetworkHelper(context: Context): NetworkHelper {
    return NetworkHelper(context)
}

private fun provideBaseUrl(): String {
//    return "http://192.168.43.35/shirzadProject/"
    return " https://jsonplaceholder.typicode.com/"


}

private fun provideOkHttpClient(): OkHttpClient {
    try {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(
                chain: Array<X509Certificate>,
                authType: String,
            ) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(
                chain: Array<X509Certificate>,
                authType: String,
            ) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        })

        // Install the all-trusting trust manager
        val sslContext =
            SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())

        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory = sslContext.socketFactory

        // Create an loggingInterCeptor
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier { _, _ -> true }
            .addInterceptor(logging)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

    } catch (e: Exception) {
        throw RuntimeException(e)
    }


}

private fun provideRetrofi(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()
}


private fun provideApiService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}

