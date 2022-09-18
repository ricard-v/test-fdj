package com.mackosoft.features.homepage

import com.mackosoft.features.homepage.model.entities.FootballTeamEntity

interface HomePageContract {
    interface View {
        fun showLoading(isLoading: Boolean)
        fun showSearchResults(results: List<FootballTeamEntity>)
        fun showNoResultsFound(show: Boolean, searchKey: String)
        fun showErrorMessage(errorMessage: String)
    }

    interface Presenter {
        fun searchChampionship(searchKey: String)
    }

    interface Model {
        suspend fun getFootballTeamsFromChampionShip(championShip: String): Result<List<FootballTeamEntity>>
    }
}