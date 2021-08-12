package com.heartsun.pithuwakhanipani.di

import com.heartsun.pithuwakhanipani.data.database.KanipaniDatabase
import com.heartsun.pithuwakhanipani.data.repository.AuthRepoImpl
import com.heartsun.pithuwakhanipani.data.repository.AuthRepository
import com.heartsun.pithuwakhanipani.data.repository.ConnectionToServer
import com.heartsun.pithuwakhanipani.data.repository.databaseReppo.DbRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository> { AuthRepoImpl(get(), get(), get(), get(), get()) }
    single { DbRepository(get()) }
    single { KanipaniDatabase.getKanipaniDatabase(androidContext(), get()) }
    single { ConnectionToServer(get()) }

}