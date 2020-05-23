package ru.home.collaborativeeducation.di

import android.content.Context
import dagger.Provides
import ru.home.collaborativeeducation.network.Api
import ru.home.collaborativeeducation.network.NetworkRepository
import ru.home.collaborativeeducation.repository.InternalStorageRepository
import javax.inject.Singleton

@dagger.Module(includes = [NetworkModule::class])
class Module(val context: Context) {

    @Singleton
    @Provides
    fun providesNetworkReposotu(api: Api): NetworkRepository {
        return NetworkRepository(api)
    }

    @Singleton
    @Provides
    fun providesInternalStorageRepository(): InternalStorageRepository {
        return InternalStorageRepository(context)
    }
}