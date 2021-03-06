package com.github.members.directory.di

import com.github.members.directory.features.details.DetailsViewModel
import com.github.members.directory.features.history.HistoryViewModel
import com.github.members.directory.features.search.SearchViewModel
import com.github.members.directory.features.users.MembersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MembersViewModel(integrator = get()) }
    viewModel { DetailsViewModel(integrator = get(), context = get()) }
    viewModel { SearchViewModel(integrator = get()) }
    viewModel { HistoryViewModel(integrator = get()) }
}