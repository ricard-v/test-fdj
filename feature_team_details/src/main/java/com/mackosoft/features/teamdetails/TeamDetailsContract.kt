package com.mackosoft.features.teamdetails

import com.mackosoft.features.teamdetails.model.entities.FootballTeamDetailsEntity

interface TeamDetailsContract {
    interface View {
        fun showLoading(isLoading: Boolean)
        fun showTeamDetails(teamDetailsEntity: FootballTeamDetailsEntity)
        fun showErrorMessage(errorMessage: String)
    }

    interface Presenter {
        fun getFootballTeamDetails(teamId: String)
    }

    interface Model {
        suspend fun getFootballTeamDetails(id: String): Result<FootballTeamDetailsEntity>
    }
}