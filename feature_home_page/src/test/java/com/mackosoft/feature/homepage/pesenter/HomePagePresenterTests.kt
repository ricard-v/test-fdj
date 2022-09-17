package com.mackosoft.feature.homepage.pesenter

import com.mackosoft.core.test.base.presenter.BasePresenterTests
import com.mackosoft.feature.homepage.HomePageContract
import com.mackosoft.feature.homepage.model.HomePageModel
import com.mackosoft.feature.homepage.model.entities.FootballTeamEntity
import com.mackosoft.feature.homepage.presenter.HomePagePresenter
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomePagePresenterTests :
    BasePresenterTests<HomePageModel, HomePageContract.View, HomePagePresenter>() {

    override val mockedModel: HomePageModel = mockk()
    override val mockedView: HomePageContract.View = mockk()
    override val presenter: HomePagePresenter = HomePagePresenter(
        view = mockedView,
        mainDispatcher = Dispatchers.Main, // Main Dispatcher is handled in BasePresenterTests
        homePageModel = mockedModel,
    )

    @Test
    fun `When Model returns a successful result for getFootballTeamsFromChampionShip()`() =
        testScope.runTest {
            // arrange
            val tSearchKey = "Ligue 1"
            val tTeams = listOf(
                FootballTeamEntity(id = "1", teamBadgeUrl = "url/to/image1.png"),
                FootballTeamEntity(id = "2", teamBadgeUrl = "url/to/image2.png"),
                FootballTeamEntity(id = "3", teamBadgeUrl = "url/to/image3.png"),
            )

            coEvery {
                mockedModel.getFootballTeamsFromChampionShip(championShip = tSearchKey)
            } answers {
                Result.success(tTeams)
            }
            every { mockedView.showLoading(any()) } just Runs
            every { mockedView.showNoResultsFound(any(), any()) } just Runs
            every { mockedView.showSearchResults(results = tTeams) } just Runs

            // act
            presenter.searchChampionship(tSearchKey)
            advanceUntilIdle()

            // assert
            coVerify(exactly = 1) {
                mockedModel.getFootballTeamsFromChampionShip(championShip = tSearchKey)
            }

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

            verify(exactly = 2) {
                mockedView.showNoResultsFound(
                    withArg {
                        val show = actual as Boolean
                        assert(!show)
                    },
                    withArg {
                        val searchKey = actual as String
                        assert(searchKey == "")
                    },
                )
            }

            verify(exactly = 1) {
                mockedView.showSearchResults(
                    withArg {
                        @Suppress("UNCHECKED_CAST")
                        val result = (actual as List<FootballTeamEntity>)
                        assert(result == tTeams)
                    }
                )
            }
        }

    @Test
    fun `When Model finds no results for getFootballTeamsFromChampionShip()`() =
        testScope.runTest {
            // arrange
            val tSearchKey = "This league does not exist!"
            val tTeams = emptyList<FootballTeamEntity>()

            coEvery {
                mockedModel.getFootballTeamsFromChampionShip(championShip = tSearchKey)
            } answers {
                Result.success(tTeams)
            }
            every { mockedView.showLoading(any()) } just Runs
            every { mockedView.showNoResultsFound(any(), any()) } just Runs
            every { mockedView.showSearchResults(results = tTeams) } just Runs

            // act
            presenter.searchChampionship(tSearchKey)
            advanceUntilIdle()

            // assert
            coVerify(exactly = 1) {
                mockedModel.getFootballTeamsFromChampionShip(championShip = tSearchKey)
            }

            verify(exactly = 1) {
                mockedView.showLoading(withArg {
                    val isLoading = actual as Boolean
                    assert(isLoading)
                })
            }

            verify(exactly = 1) {
                mockedView.showLoading(withArg {
                    val isLoading = actual as Boolean
                    assert(!isLoading)
                })
            }

            verify(exactly = 1) {
                mockedView.showNoResultsFound(
                    withArg {
                        val show = actual as Boolean
                        assert(show)
                    },
                    withArg {
                        val searchKey = actual as String
                        assert(searchKey == tSearchKey)
                    },
                )
            }

            verify(exactly = 1) {
                mockedView.showSearchResults(
                    withArg {
                        @Suppress("UNCHECKED_CAST")
                        val result = (actual as List<FootballTeamEntity>)
                        assert(result == tTeams)
                    }
                )
            }
        }

    @Test
    fun `When Model returns an unsuccessful result for getFootballTeamsFromChampionShip()`() =
        testScope.runTest {
            // arrange
            val tSearchKey = "This league does not exist!"
            val tException = Exception("League not found")

            coEvery {
                mockedModel.getFootballTeamsFromChampionShip(championShip = tSearchKey)
            } answers {
                Result.failure(tException)
            }
            every { mockedView.showLoading(any()) } just Runs
            every { mockedView.showSearchResults(emptyList()) } just Runs
            every { mockedView.showNoResultsFound(any(), any()) } just Runs
            every { mockedView.showErrorMessage(any()) } just Runs

            // act
            presenter.searchChampionship(tSearchKey)
            advanceUntilIdle()

            // assert
            coVerify(exactly = 1) {
                mockedModel.getFootballTeamsFromChampionShip(championShip = tSearchKey)
            }

            verify(exactly = 1) {
                mockedView.showSearchResults(
                    withArg {
                        @Suppress("UNCHECKED_CAST")
                        val result = (actual as List<FootballTeamEntity>)
                        assert(result.isEmpty())
                    }
                )
            }

            verify(exactly = 1) {
                mockedView.showErrorMessage(withArg {
                    val result = (actual as String)
                    assert(result == tException.localizedMessage!!)
                })
            }
        }
}