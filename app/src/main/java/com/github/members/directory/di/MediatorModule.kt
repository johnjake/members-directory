package com.github.members.directory.di

import androidx.paging.ExperimentalPagingApi
import com.github.members.directory.features.users.repository.PagingMediatorMembers
import org.koin.dsl.module

@ExperimentalPagingApi
val mediatorModule = module {
    factory { PagingMediatorMembers(apiServices = get(), appDatabase = get()) }
}