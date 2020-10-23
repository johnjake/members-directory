package com.github.members.directory.di

import com.github.members.directory.features.users.MembersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MembersViewModel(integrator = get()) }
}