package com.mackosoft.feature.homepage.datasource

import com.mackosoft.core.base.datasource.BaseRemoteDataSource
import com.mackosoft.core.base.datasource.api.RetrofitClient
import com.mackosoft.feature.homepage.datasource.api.HomePageApi
import com.mackosoft.feature.homepage.datasource.data.FootballChampionshipData
import com.mackosoft.feature.homepage.datasource.data.FootballTeamData
import javax.inject.Inject

class HomePageRemoteDataSource @Inject constructor(
    retrofitClient: RetrofitClient,
) : BaseRemoteDataSource(retrofitClient) {

    suspend fun getAllChampionsShips(): Result<List<FootballChampionshipData>> {
        return getRemoteData(
            apiCall = {
                HomePageApi::class.java.buildApi().getFootballChampionships()
            },
            apiCallName = "getFootballChampionships",
        )
    }

    suspend fun getFootballTeamsFromChampionShip(
        championShip: String,
    ): Result<List<FootballTeamData>> {
        return getRemoteData(
            apiCall = {
                HomePageApi::class.java.buildApi().getChampionshipByName(name = championShip)
            },
            apiCallName = "getFootballTeamsFromChampionShip(name = $championShip)",
        )
    }
}