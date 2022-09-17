package com.mackosoft.features.homepage.datasource

import com.mackosoft.core.base.datasource.api.RetrofitClient
import com.mackosoft.core.base.datasource.BaseRemoteDataSource
import com.mackosoft.features.homepage.datasource.api.HomePageApi
import com.mackosoft.features.homepage.datasource.data.FootballChampionsShips
import com.mackosoft.features.homepage.datasource.data.FootballTeams
import javax.inject.Inject

class HomePageRemoteDataSource @Inject constructor(
    retrofitClient: RetrofitClient,
) : BaseRemoteDataSource(retrofitClient) {

    suspend fun getAllChampionsShips(): Result<FootballChampionsShips> {
        return getRemoteData(
            apiCall = {
                HomePageApi::class.java.buildApi().getFootballChampionships()
            },
            apiCallName = "getFootballChampionships",
        )
    }

    suspend fun getFootballTeamsFromChampionShip(
        championShip: String,
    ): Result<FootballTeams> {
        return getRemoteData(
            apiCall = {
                HomePageApi::class.java.buildApi().getChampionshipByName(name = championShip)
            },
            apiCallName = "getFootballTeamsFromChampionShip(name = $championShip)",
        )
    }
}