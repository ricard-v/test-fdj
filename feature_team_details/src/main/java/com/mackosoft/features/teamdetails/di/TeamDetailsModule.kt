package com.mackosoft.features.teamdetails.di

import androidx.fragment.app.Fragment
import com.mackosoft.core.base.datasource.api.RetrofitClient
import com.mackosoft.core.dispatchers.DefaultDispatcher
import com.mackosoft.core.dispatchers.IoDispatcher
import com.mackosoft.core.dispatchers.MainDispatcher
import com.mackosoft.features.teamdetails.TeamDetailsContract
import com.mackosoft.features.teamdetails.datasource.TeamDetailsRemoteDataSource
import com.mackosoft.features.teamdetails.model.TeamDetailsModel
import com.mackosoft.features.teamdetails.presenter.TeamDetailsPresenter
import com.mackosoft.features.teamdetails.view.TeamDetailsFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(FragmentComponent::class)
abstract class TeamDetailsBindings {
    @Binds
    abstract fun bindsView(view: TeamDetailsFragment): TeamDetailsContract.View

    @Binds
    abstract fun bindsPresenter(presenter: TeamDetailsPresenter): TeamDetailsContract.Presenter
}

@Module
@InstallIn(FragmentComponent::class)
object TeamDetailsModule {
    @Provides
    fun providesRemoteDataSource(retrofitClient: RetrofitClient): TeamDetailsRemoteDataSource {
        return TeamDetailsRemoteDataSource(retrofitClient)
    }

    @Provides
    fun providesTeamDetailsModel(
        remoteDataSource: TeamDetailsRemoteDataSource,
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ) : TeamDetailsModel {
        return TeamDetailsModel(remoteDataSource, defaultDispatcher, ioDispatcher)
    }

    @Provides
    fun providesTeamDetailsView(view: Fragment): TeamDetailsFragment = view as TeamDetailsFragment
}