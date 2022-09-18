package com.mackosoft.features.teamdetails.presenter

import com.mackosoft.core.test.base.presenter.BasePresenterTests
import com.mackosoft.features.teamdetails.TeamDetailsContract
import com.mackosoft.features.teamdetails.model.TeamDetailsModel
import com.mackosoft.features.teamdetails.model.entities.FootballTeamDetailsEntity
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TeamDetailsPresenterTests :
    BasePresenterTests<TeamDetailsModel, TeamDetailsContract.View, TeamDetailsPresenter>() {

    override val mockedModel: TeamDetailsModel = mockk()
    override val mockedView: TeamDetailsContract.View = mockk()
    override val presenter: TeamDetailsPresenter = TeamDetailsPresenter(
        view = mockedView,
        mainDispatcher = Dispatchers.Main,
        teamDetailsModel = mockedModel,
    )

    @Test
    fun `When Model returns successful result for getFootballTeamDetails()`() = testScope.runTest {
        // arrange
        val tTeamId = "1"
        val tTeamDetails = FootballTeamDetailsEntity(
            bannerUrl = "path/to/banner_1.png",
            country = "France",
            championShipName = "French League 1",
            description = "Description Équipe 1",
        )

        coEvery {
            mockedModel.getFootballTeamDetails(id = tTeamId)
        } answers {
            Result.success(tTeamDetails)
        }

        coEvery { mockedView.showLoading(any()) } just Runs
        coEvery { mockedView.showTeamDetails(teamDetailsEntity = tTeamDetails) } just Runs

        // act
        presenter.getFootballTeamDetails(teamId = tTeamId)
        advanceUntilIdle()

        // assert
        verify(exactly = 1) {
            mockedView.showLoading(
                withArg {
                    val isLoading = actual as Boolean
                    assert(isLoading)
                }
            )
        }

        verify(exactly = 1) {
            mockedView.showLoading(
                withArg {
                    val isLoading = actual as Boolean
                    assert(!isLoading)
                }
            )
        }

        verify(exactly = 1) {
            mockedView.showTeamDetails(
                withArg {
                    val result = actual as FootballTeamDetailsEntity
                    assert(result.bannerUrl == tTeamDetails.bannerUrl)
                    assert(result.country == tTeamDetails.country)
                    assert(result.championShipName == tTeamDetails.championShipName)
                    assert(result.description == tTeamDetails.description)
                }
            )
        }
    }

    @Test
    fun `When Model finds no details for getFootballTeamDetails()`() = testScope.runTest {
        // arrange
        val tTeamId = "this team does not exist"
        val tException = Exception("Team details not found with id <$tTeamId>")

        coEvery {
            mockedModel.getFootballTeamDetails(id = tTeamId)
        } answers {
            Result.failure(tException)
        }

        coEvery { mockedView.showLoading(any()) } just Runs
        coEvery { mockedView.showErrorMessage(any()) } just Runs

        // act
        presenter.getFootballTeamDetails(teamId = tTeamId)
        advanceUntilIdle()

        // assert
        verify(exactly = 1) {
            mockedView.showErrorMessage(
                withArg {
                    val result = actual as String
                    assert(result == tException.localizedMessage!!)
                }
            )
        }
    }
}