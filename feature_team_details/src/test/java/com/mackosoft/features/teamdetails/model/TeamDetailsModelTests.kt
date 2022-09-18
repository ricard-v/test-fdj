package com.mackosoft.features.teamdetails.model

import com.mackosoft.core.data.FootballTeamData
import com.mackosoft.core.data.FootballTeams
import com.mackosoft.core.test.base.model.BaseModelTests
import com.mackosoft.features.teamdetails.datasource.TeamDetailsRemoteDataSource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TeamDetailsModelTests : BaseModelTests<TeamDetailsRemoteDataSource, TeamDetailsModel>() {

    override val mockedRemoteDatasource: TeamDetailsRemoteDataSource = mockk()
    override val model: TeamDetailsModel = TeamDetailsModel(
        remoteDataSource = mockedRemoteDatasource,
        defaultDispatcher = defaultDispatcher,
        ioDispatcher = ioDispatcher,
    )

    @Test
    fun `When TeamDetailsRemoteDataSource returns successful answer`() = testScope.runTest {
        // arrange
        val tTeamId = "1"
        val tTeams = FootballTeams(
            teams = listOf(
                FootballTeamData(
                    idTeam = tTeamId,
                    strBanner = "path/to/banner_1.png",
                    strCountry = "France",
                    strLeague = "French League 1",
                    strDescriptionFR = "Description Équipe 1",
                )
            )
        )
        coEvery {
            mockedRemoteDatasource.getFootballTeamDetails(id = tTeamId)
        } answers {
            Result.success(tTeams)
        }

        // act
        val result = model.getFootballTeamDetails(id = tTeamId)
        advanceUntilIdle()

        // assert
        assert(result.isSuccess)
        val data = result.getOrNull()!!
        assert(data.bannerUrl == tTeams.teams.first().strBanner)
        assert(data.country == tTeams.teams.first().strCountry)
        assert(data.championShipName == tTeams.teams.first().strLeague)
        assert(data.description == tTeams.teams.first().strDescriptionFR)
    }

    @Test
    fun `When TeamDetailsRemoteDataSource failed find team details`() = testScope.runTest {
        // arrange
        val tTeamId = "-123242"
        val tException = Exception("Team id <$tTeamId> does not exist")
        coEvery {
            mockedRemoteDatasource.getFootballTeamDetails(id = tTeamId)
        } answers {
            Result.failure(tException)
        }

        // act
        val result = model.getFootballTeamDetails(id = tTeamId)
        advanceUntilIdle()

        // assert
        assert(result.isFailure)
        assert(result.exceptionOrNull()!!.message == tException.message)
    }
}