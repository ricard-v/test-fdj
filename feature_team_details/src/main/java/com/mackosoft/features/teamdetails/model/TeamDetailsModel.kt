package com.mackosoft.features.teamdetails.model

import android.util.Log
import com.mackosoft.core.base.model.BaseModel
import com.mackosoft.core.data.FootballTeamData
import com.mackosoft.core.data.FootballTeams
import com.mackosoft.core.dispatchers.DefaultDispatcher
import com.mackosoft.core.dispatchers.IoDispatcher
import com.mackosoft.features.teamdetails.TeamDetailsContract
import com.mackosoft.features.teamdetails.datasource.TeamDetailsRemoteDataSource
import com.mackosoft.features.teamdetails.model.entities.FootballTeamDetailsEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TeamDetailsModel @Inject constructor(
    remoteDataSource: TeamDetailsRemoteDataSource,
    @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
) : BaseModel<TeamDetailsRemoteDataSource>(remoteDataSource, ioDispatcher, defaultDispatcher),
    TeamDetailsContract.Model {

    private companion object {
        const val TAG = "TeamDetailsModel"
    }

    override suspend fun getFootballTeamDetails(id: String): Result<FootballTeamDetailsEntity> {
        val teamDetailsData: Result<FootballTeams> = withContext(ioDispatcher) {
            remoteDataSource.getFootballTeamDetails(id = id)
        }

        teamDetailsData.onFailure {
            return Result.failure(it)
        }

        val data: FootballTeamData = teamDetailsData.getOrNull()?.teams?.firstOrNull() ?: run {
            Log.e(
                TAG,
                "Missing football team data in $teamDetailsData with id <$id>"
            )
            return Result.failure(Exception("Football team with id <$id> not found"))
        }

        return Result.success(
            FootballTeamDetailsEntity(
                bannerUrl = data.strTeamBanner,
                country = data.strCountry,
                championShipName = data.strLeague,
                description = data.strDescriptionFR,
            )
        )
    }
}