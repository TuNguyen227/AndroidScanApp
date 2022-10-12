package com.nmt.stockcheck.di

import com.google.gson.Gson
import com.nmt.stockcheck.AppApplication
import com.nmt.stockcheck.BuildConfig
import com.nmt.stockcheck.api.AppApi
import com.nmt.stockcheck.config.Config
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit

val retrofitModule= module {

    fun provideAppApi(gson: Gson, okHttpClient: OkHttpClient): AppApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_OCR_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(com.haroldadmin.cnradapter.NetworkResponseAdapterFactory())
            .client(okHttpClient)
            .build().create(AppApi::class.java)
    }


    fun provideOkHttpClient(app: AppApplication): OkHttpClient {
        val httpInterceptor = HttpLoggingInterceptor()
        httpInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val headerInterceptor = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request: Request =
                    chain.request().newBuilder().addHeader("X-Feedjoy-Lang", "en").build()
                return chain.proceed(request)
            }
        }
        val cacheDir = File(app.cacheDir, UUID.randomUUID().toString())
        // 10 MiB cache
        val cache = Cache(cacheDir, 10 * 1024 * 1024)
        return OkHttpClient.Builder()
            .cache(null)
            .connectTimeout(Config.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(Config.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(Config.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
            .addInterceptor(headerInterceptor)
            .addInterceptor(httpInterceptor)
            .build()
    }

    single { provideOkHttpClient(androidApplication() as AppApplication) }
    single { provideAppApi(get(),get())}
}