package com.nmt.stockcheck.di

import com.nmt.stockcheck.AppApplication
import com.nmt.stockcheck.viewmodel.CameraViewModel
import com.nmt.stockcheck.viewmodel.ScanViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule= module {
    viewModel{
        CameraViewModel(androidApplication() as AppApplication,get())
    }
    viewModel{
        ScanViewModel(androidApplication() as AppApplication,get())
    }
}