package com.mackosoft.features.homepage.datasource.api

import com.mackosoft.features.homepage.datasource.data.FootballChampionsShips
import com.mackosoft.core.data.FootballTeams
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomePageApi {
    @GET("all_leagues.php")
    suspend fun getFootballChampionships(): Response<FootballChampionsShips>

    @GET("search_all_teams.php")
    suspend fun getChampionshipByName(@Query("l") name: String): Response<FootballTeams>
}