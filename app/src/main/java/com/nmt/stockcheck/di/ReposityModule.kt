package com.nmt.stockcheck.di

import com.nmt.stockcheck.repository.BaseRepository
import org.koin.dsl.module

val reposityModule= module {
    single {
        BaseRepository(get())
    }
}