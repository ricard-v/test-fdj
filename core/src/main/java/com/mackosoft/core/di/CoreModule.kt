package com.mackosoft.core.di

import com.mackosoft.core.base.datasource.api.RetrofitClient
import com.mackosoft.core.dispatchers.DefaultDispatcher
import com.mackosoft.core.dispatchers.IoDispatcher
import com.mackosoft.core.dispatchers.MainDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Provides
    fun providesRetrofitClient(): RetrofitClient = RetrofitClient

    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}