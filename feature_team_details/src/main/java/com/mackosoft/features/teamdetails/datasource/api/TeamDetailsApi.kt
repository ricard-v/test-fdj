package com.mackosoft.features.teamdetails.datasource.api

import com.mackosoft.core.data.FootballTeams
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TeamDetailsApi {
    @GET("lookupteam.php")
    suspend fun getFootballTeamDetails(@Query("id") id: String): Response<FootballTeams>
}