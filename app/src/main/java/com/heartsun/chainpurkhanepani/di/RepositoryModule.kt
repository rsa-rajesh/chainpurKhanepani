package com.heartsun.chainpurkhanepani.di

import com.heartsun.chainpurkhanepani.data.database.KanipaniDatabase
import com.heartsun.chainpurkhanepani.data.repository.AuthRepoImpl
import com.heartsun.chainpurkhanepani.data.repository.AuthRepository
import com.heartsun.chainpurkhanepani.data.repository.ConnectionToServer
import com.heartsun.chainpurkhanepani.data.repository.databaseReppo.DbRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository> { AuthRepoImpl(get(), get(), get(), get(), get()) }
    single { DbRepository(get()) }
    single { KanipaniDatabase.getKanipaniDatabase(androidContext(), get()) }
    single { ConnectionToServer(get()) }
}