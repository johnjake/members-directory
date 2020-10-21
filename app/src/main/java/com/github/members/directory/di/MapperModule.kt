package com.github.members.directory.di

import com.github.members.directory.data.mapper.MembersMapper
import org.koin.dsl.module

val mapperModule = module {
    single { provideMembersMapper() }
}

fun provideMembersMapper(): MembersMapper {
    return MembersMapper.getInstance()
}