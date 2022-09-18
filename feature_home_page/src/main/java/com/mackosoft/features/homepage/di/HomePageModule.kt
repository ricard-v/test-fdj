package com.mackosoft.features.homepage.di

import androidx.fragment.app.Fragment
import com.mackosoft.core.base.datasource.api.RetrofitClient
import com.mackosoft.core.dispatchers.DefaultDispatcher
import com.mackosoft.core.dispatchers.IoDispatcher
import com.mackosoft.core.dispatchers.MainDispatcher
import com.mackosoft.features.homepage.HomePageContract
import com.mackosoft.features.homepage.datasource.HomePageRemoteDataSource
import com.mackosoft.features.homepage.model.HomePageModel
import com.mackosoft.features.homepage.presenter.HomePagePresenter
import com.mackosoft.features.homepage.view.HomePageFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(FragmentComponent::class)
abstract class HomePageBindings {
    @Binds
    abstract fun bindsView(view: HomePageFragment): HomePageContract.View

    @Binds
    abstract fun bindsPresenter(presenter: HomePagePresenter): HomePageContract.Presenter
}

@Module
@InstallIn(FragmentComponent::class)
object HomePageModule {
    @Provides
    fun providesRemoteDataSource(retrofitClient: RetrofitClient): HomePageRemoteDataSource {
        return HomePageRemoteDataSource(retrofitClient)
    }

    @Provides
    fun providesHomePageModel(
        remoteDataSource: HomePageRemoteDataSource,
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): HomePageModel {
        return HomePageModel(remoteDataSource, defaultDispatcher, ioDispatcher)
    }

    @Provides
    fun providesHomePageView(view: Fragment): HomePageFragment = view as HomePageFragment
}