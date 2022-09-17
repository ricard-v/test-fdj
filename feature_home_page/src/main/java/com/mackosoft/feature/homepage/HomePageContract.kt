package com.mackosoft.feature.homepage

import com.mackosoft.feature.homepage.model.entities.FootballTeamEntity

interface HomePageContract {
    interface View {
        fun showSearchResults(results: List<FootballTeamEntity>)
        fun showErrorMessage(errorMessage: String)
    }

    interface Presenter {
        fun searchChampionship(searchKey: String)
    }

    interface Model {
        suspend fun getFootballTeamsFromChampionShip(championShip: String): Result<List<FootballTeamEntity>>
    }
}