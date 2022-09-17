package com.mackosoft.feature.homepage.datasource.api

import com.mackosoft.feature.homepage.datasource.data.FootballChampionsShips
import com.mackosoft.feature.homepage.datasource.data.FootballChampionshipData
import com.mackosoft.feature.homepage.datasource.data.FootballTeamData
import com.mackosoft.feature.homepage.datasource.data.FootballTeams
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomePageApi {
    @GET("2/all_leagues.php")
    suspend fun getFootballChampionships(): Response<FootballChampionsShips>

    @GET("2/search_all_teams.php")
    suspend fun getChampionshipByName(@Query("l") name: String): Response<FootballTeams>
}