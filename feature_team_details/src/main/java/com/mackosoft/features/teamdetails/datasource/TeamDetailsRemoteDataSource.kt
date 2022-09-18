package com.mackosoft.features.teamdetails.datasource

import com.mackosoft.core.base.datasource.BaseRemoteDataSource
import com.mackosoft.core.base.datasource.api.RetrofitClient
import com.mackosoft.core.data.FootballTeams
import com.mackosoft.features.teamdetails.datasource.api.TeamDetailsApi
import javax.inject.Inject

class TeamDetailsRemoteDataSource @Inject constructor(
    retrofitClient: RetrofitClient,
) : BaseRemoteDataSource(retrofitClient) {

    suspend fun getFootballTeamDetails(id: String): Result<FootballTeams> {
        return getRemoteData(
            apiCall = {
                TeamDetailsApi::class.java.buildApi().getFootballTeamDetails(id = id)
            },
            apiCallName = "getFootballTeamDetails(id = $id)",
        )
    }
}