package com.mackosoft.feature.homepage.model

import android.util.Log
import com.mackosoft.core.base.model.BaseModel
import com.mackosoft.core.dispatchers.DefaultDispatcher
import com.mackosoft.core.dispatchers.IoDispatcher
import com.mackosoft.feature.homepage.HomePageContract
import com.mackosoft.feature.homepage.datasource.HomePageRemoteDataSource
import com.mackosoft.feature.homepage.datasource.data.FootballChampionsShips
import com.mackosoft.feature.homepage.datasource.data.FootballChampionshipData
import com.mackosoft.feature.homepage.datasource.data.FootballTeams
import com.mackosoft.feature.homepage.model.entities.FootballTeamEntity
import kotlinx.coroutines.CoroutineDispatcher
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
        val availableChampionShipsResult: Result<FootballChampionsShips> =
            withContext(ioDispatcher) {
                remoteDataSource.getAllChampionsShips()
            }

        availableChampionShipsResult.onFailure {
            return Result.failure(it)
        }

        val matchingChampionShip: FootballChampionshipData = withContext(defaultDispatcher) {
            availableChampionShipsResult.getOrNull()?.leagues?.find {
                it.name.contains(
                    championShip,
                    ignoreCase = true,
                )
            }
        } ?: run {
            Log.e(
                TAG,
                "Could not find corresponding championship with name <$championShip> in $availableChampionShipsResult."
            )
            return Result.success(emptyList())
        }

        val championShipTeamsResult: Result<FootballTeams> = withContext(ioDispatcher) {
            remoteDataSource.getFootballTeamsFromChampionShip(
                championShip = matchingChampionShip.name
            )
        }

        return championShipTeamsResult.fold(
            onSuccess = { championShipTeams ->
                Result.success(
                    withContext(defaultDispatcher) {
                        championShipTeams.teams.map { data ->
                            FootballTeamEntity(
                                id = data.idTeam,
                                teamBadgeUrl = data.strTeamBadge,
                                teamName = data.strTeam,
                            )
                        }
                    }
                )
            },
            onFailure = { Result.failure(it) }
        )
    }
}