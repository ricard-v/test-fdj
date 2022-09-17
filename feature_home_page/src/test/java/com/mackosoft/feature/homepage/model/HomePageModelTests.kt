package com.mackosoft.feature.homepage.model

import com.mackosoft.core.test.base.model.BaseModelTests
import com.mackosoft.feature.homepage.datasource.HomePageRemoteDataSource
import com.mackosoft.feature.homepage.datasource.data.FootballChampionsShips
import com.mackosoft.feature.homepage.datasource.data.FootballChampionshipData
import com.mackosoft.feature.homepage.datasource.data.FootballTeamData
import com.mackosoft.feature.homepage.datasource.data.FootballTeams
import com.mackosoft.feature.homepage.model.entities.FootballTeamEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class HomePageModelTests : BaseModelTests<HomePageRemoteDataSource, HomePageModel>() {
    override val mockedRemoteDatasource: HomePageRemoteDataSource = mockk()
    override val model: HomePageModel = HomePageModel(
        remoteDataSource = mockedRemoteDatasource,
        defaultDispatcher = defaultDispatcher,
        ioDispatcher = ioDispatcher,
    )

    @Test
    fun `When HomePageRemoteDataSource returns successful answers`() = testScope.runTest {
        // arrange
        val tChampionShips = FootballChampionsShips(
            leagues = listOf(
                FootballChampionshipData(id = "1", name = "French Ligue 1"),
                FootballChampionshipData(id = "2", name = "English Premier League"),
                FootballChampionshipData(id = "3", name = "Italian Serie A"),
            )
        )
        coEvery {
            mockedRemoteDatasource.getAllChampionsShips()
        } answers {
            Result.success(tChampionShips)
        }

        val tLeague = tChampionShips.leagues.first()
        val tFootballTeams = FootballTeams(
            teams = listOf(
                FootballTeamData(idTeam = "1", strTeamBadge = "url/to/image1.png"),
                FootballTeamData(idTeam = "2", strTeamBadge = "url/to/image2.png"),
                FootballTeamData(idTeam = "3", strTeamBadge = "url/to/image3.png"),
            )
        )
        coEvery {
            mockedRemoteDatasource.getFootballTeamsFromChampionShip(championShip = tLeague.name)
        } answers {
            Result.success(tFootballTeams)
        }

        // act
        val result = model.getFootballTeamsFromChampionShip(championShip = tLeague.name)
        advanceUntilIdle()

        // assert
        assertResultIsSuccess(expected = tFootballTeams, actual = result)

        coVerify(exactly = 1) {
            mockedRemoteDatasource.getAllChampionsShips()
        }
        coVerify(exactly = 1) {
            mockedRemoteDatasource.getFootballTeamsFromChampionShip(
                withArg {
                    val name = actual as String
                    assert(name == tLeague.name)
                }
            )
        }
    }

    @Test
    fun `When HomePageRemoteDataSource returns successful answers with partial championship name`() =
        testScope.runTest {
            // arrange
            val tPartialChampionShipName = "Ligue 1"

            val tChampionShips = FootballChampionsShips(
                leagues = listOf(
                    FootballChampionshipData(id = "1", name = "French Ligue 1"),
                    FootballChampionshipData(id = "2", name = "English Premier League"),
                    FootballChampionshipData(id = "3", name = "Italian Serie A"),
                )
            )
            coEvery {
                mockedRemoteDatasource.getAllChampionsShips()
            } answers {
                Result.success(tChampionShips)
            }

            val tLeague = tChampionShips.leagues.first()
            val tFootballTeams = FootballTeams(
                teams = listOf(
                    FootballTeamData(idTeam = "1", strTeamBadge = "url/to/image1.png"),
                    FootballTeamData(idTeam = "2", strTeamBadge = "url/to/image2.png"),
                    FootballTeamData(idTeam = "3", strTeamBadge = "url/to/image3.png"),
                )
            )
            coEvery {
                mockedRemoteDatasource.getFootballTeamsFromChampionShip(
                    championShip = tLeague.name,
                )
            } answers {
                Result.success(tFootballTeams)
            }

            // act
            val result = model.getFootballTeamsFromChampionShip(
                championShip = tPartialChampionShipName,
            )
            advanceUntilIdle()

            // assert
            assertResultIsSuccess(expected = tFootballTeams, actual = result)

            coVerify(exactly = 1) {
                mockedRemoteDatasource.getAllChampionsShips()
            }
            coVerify(exactly = 1) {
                mockedRemoteDatasource.getFootballTeamsFromChampionShip(
                    withArg {
                        val name = actual as String
                        assert(name == tLeague.name)
                    }
                )
            }
        }

    @Test
    fun `When HomePageRemoteDataSource returns successful answers and is case insensitive`() =
        testScope.runTest {
            // arrange
            val tPartialChampionShipName = "LiGUe 1"

            val tChampionShips = FootballChampionsShips(
                leagues = listOf(
                    FootballChampionshipData(id = "1", name = "French Ligue 1"),
                    FootballChampionshipData(id = "2", name = "English Premier League"),
                    FootballChampionshipData(id = "3", name = "Italian Serie A"),
                )
            )
            coEvery {
                mockedRemoteDatasource.getAllChampionsShips()
            } answers {
                Result.success(tChampionShips)
            }

            val tLeague = tChampionShips.leagues.first()
            val tFootballTeams = FootballTeams(
                teams = listOf(
                    FootballTeamData(idTeam = "1", strTeamBadge = "url/to/image1.png"),
                    FootballTeamData(idTeam = "2", strTeamBadge = "url/to/image2.png"),
                    FootballTeamData(idTeam = "3", strTeamBadge = "url/to/image3.png"),
                )
            )
            coEvery {
                mockedRemoteDatasource.getFootballTeamsFromChampionShip(
                    championShip = tLeague.name,
                )
            } answers {
                Result.success(tFootballTeams)
            }

            // act
            val result = model.getFootballTeamsFromChampionShip(
                championShip = tPartialChampionShipName,
            )
            advanceUntilIdle()

            // assert
            assertResultIsSuccess(expected = tFootballTeams, actual = result)

            coVerify(exactly = 1) {
                mockedRemoteDatasource.getAllChampionsShips()
            }
            coVerify(exactly = 1) {
                mockedRemoteDatasource.getFootballTeamsFromChampionShip(
                    withArg {
                        val name = actual as String
                        assert(name == tLeague.name)
                    }
                )
            }
        }


    @Test
    fun `When HomePageRemoteDataSource failed to return all championships`() = testScope.runTest {
        // arrange
        val tException = IOException("Connection timeout")
        coEvery {
            mockedRemoteDatasource.getAllChampionsShips()
        } answers {
            Result.failure(tException)
        }

        // act
        val result = model.getFootballTeamsFromChampionShip(championShip = "Ligue 1")
        advanceUntilIdle()

        // assert
        assert(result.isFailure)
        assert(result.exceptionOrNull()!!.message == tException.message)
    }

    @Test
    fun `When HomePageRemoteDataSource failed to find matching championship`() = testScope.runTest {
        // arrange
        val tChampionShips = FootballChampionsShips(
            leagues = listOf(
                FootballChampionshipData(id = "1", name = "French Ligue 1"),
                FootballChampionshipData(id = "2", name = "English Premier League"),
                FootballChampionshipData(id = "3", name = "Italian Serie A"),
            )
        )
        coEvery {
            mockedRemoteDatasource.getAllChampionsShips()
        } answers {
            Result.success(tChampionShips)
        }

        // failing part
        val tException = IOException("Connection timeout")
        coEvery {
            mockedRemoteDatasource.getAllChampionsShips()
        } answers {
            Result.failure(tException)
        }

        // act
        val result = model.getFootballTeamsFromChampionShip(championShip = "Ligue 1")
        advanceUntilIdle()

        // assert
        assert(result.isFailure)
        assert(result.exceptionOrNull()!!.message == tException.message)
    }


    private fun assertResultIsSuccess(
        expected: FootballTeams,
        actual: Result<List<FootballTeamEntity>>,
    ): Boolean {
        if (actual.isFailure) return false

        val result = actual.getOrNull()!!
        if (result.size != expected.teams.size) return false

        expected.teams.forEachIndexed { index, footballTeamData ->
            val got = result[index]
            val expt = FootballTeamEntity(
                id = footballTeamData.idTeam,
                teamBadgeUrl = footballTeamData.strTeamBadge,
            )
            if (got != expt) return false
        }

        return true
    }
}