package com.nmt.stockcheck

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.google.gson.Gson
import com.nmt.stockcheck.di.reposityModule
import com.nmt.stockcheck.di.retrofitModule
import com.nmt.stockcheck.di.singleTonModule
import com.nmt.stockcheck.di.viewModelModule
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class AppApplication : MultiDexApplication() {
    companion object{
        lateinit var instance:AppApplication

    }
    val gson: Gson = Gson()

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this@AppApplication
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(instance)
            modules(viewModelModule, reposityModule, retrofitModule, singleTonModule)
        }
    }

}