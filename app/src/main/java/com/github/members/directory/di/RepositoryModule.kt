package com.github.members.directory.di

import com.github.members.directory.features.members.repository.PagingRepositoryMembers
import org.koin.dsl.module

val repositoryModule = module {
    factory { PagingRepositoryMembers(apiServices = get(), appDatabase = get()) }
}