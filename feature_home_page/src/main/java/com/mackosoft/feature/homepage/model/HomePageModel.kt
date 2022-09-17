package com.mackosoft.feature.homepage.model

import android.util.Log
import com.mackosoft.core.base.model.BaseModel
import com.mackosoft.core.dispatchers.DefaultDispatcher
import com.mackosoft.core.dispatchers.IoDispatcher
import com.mackosoft.feature.homepage.HomePageContract
import com.mackosoft.feature.homepage.datasource.HomePageRemoteDataSource
import com.mackosoft.feature.homepage.datasource.data.FootballChampionshipData
import com.mackosoft.feature.homepage.datasource.data.FootballTeamData
import com.mackosoft.feature.homepage.model.entities.FootballTeamEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomePageModel @Inject constructor(
    remoteDataSource: HomePageRemoteDataSource,
    @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
) : BaseModel<HomePageRemoteDataSource>(remoteDataSource, ioDispatcher, defaultDispatcher),
    HomePageContract.Model {

    private companion object {
        const val TAG = "HomePageModel"
    }

    override suspend fun getFootballTeamsFromChampionShip(
        championShip: String
    ): Result<List<FootballTeamEntity>> {
        val availableChampionShips: Result<List<FootballChampionshipData>> =
            withContext(ioDispatcher) {
                remoteDataSource.getAllChampionsShips()
            }

        availableChampionShips.onFailure {
            return Result.failure(it)
        }

        val matchingChampionShip: FootballChampionshipData = withContext(defaultDispatcher) {
            availableChampionShips.getOrDefault(emptyList()).find { it.name.contains(championShip) }
        } ?: run {
            Log.e(
                TAG,
                "Could not find corresponding championship with name <$championShip> in $availableChampionShips."
            )
            return Result.success(emptyList())
        }

        val footballTeams: Result<List<FootballTeamData>> = withContext(ioDispatcher) {
            remoteDataSource.getFootballTeamsFromChampionShip(
                championShip = matchingChampionShip.name
            )
        }

        return footballTeams.fold(
            onSuccess = { teams ->
                Result.success(
                    withContext(defaultDispatcher) {
                        teams.map { data ->
                            FootballTeamEntity(
                                id = data.idTeam,
                                teamBadgeUrl = data.strTeamBadge,
                            )
                        }
                    }
                )
            },
            onFailure = { Result.failure(it) }
        )
    }
}