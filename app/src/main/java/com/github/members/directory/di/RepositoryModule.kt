package com.github.members.directory.di

import com.github.members.directory.features.details.DetailsRepository
import com.github.members.directory.features.history.HistoryRepository
import com.github.members.directory.features.search.SearchRepository
import com.github.members.directory.features.users.repository.PagingRepositoryMembers
import org.koin.dsl.module

val repositoryModule = module {
    factory { PagingRepositoryMembers(apiServices = get(), appDatabase = get()) }
    factory { DetailsRepository(apiServices = get(), appDatabase = get(), context = get()) }
    factory { SearchRepository(apiServices = get(), appDatabase = get(), context = get()) }
    factory { HistoryRepository(apiServices = get(), appDatabase = get()) }
}