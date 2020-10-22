package com.github.members.directory.di

import androidx.paging.ExperimentalPagingApi
import com.github.members.directory.features.members.repository.PagingRepositoryMembers
import org.koin.dsl.module

@ExperimentalPagingApi
val repositoryModule = module {
    factory { PagingRepositoryMembers(apiServices = get(), appDatabase = get(), mapper = get()) }
}