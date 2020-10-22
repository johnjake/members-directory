package com.github.members.directory.di

import androidx.paging.ExperimentalPagingApi
import com.github.members.directory.features.members.MembersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalPagingApi
val viewModelModule = module {
    viewModel { MembersViewModel(integrator = get()) }
}