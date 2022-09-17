package com.mackosoft.feature.homepage.datasource

import com.mackosoft.core.base.datasource.api.RetrofitClient
import com.mackosoft.core.test.base.network.BaseRemoteDataSourceTests
import com.mackosoft.core.test.base.network.setResponseAndEnqueue
import com.mackosoft.core.test.utils.JsonReaderUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomePageRemoteDataSourceTests : BaseRemoteDataSourceTests<HomePageRemoteDataSource>() {
    private companion object {
        const val INVALID_CHAMPIONSHIPS_LIST_PATH =
            "json/failure/invalid_champions_ships_data.json"
        const val INVALID_FOOTBALL_TEAMS_LIST_PATH =
            "json/failure/invalid_football_teams_data.json"

        const val VALID_CHAMPIONSHIPS_LIST_PATH =
            "json/success/valid_champions_ships_data.json"
        const val VALID_FOOTBALL_TEAMS_LIST_PATH =
            "json/success/valid_football_teams_data.json"
    }

    override val remoteDataSource: HomePageRemoteDataSource =
        HomePageRemoteDataSource(RetrofitClient)

    // region Success
    @Test
    fun `When server returns valid response for getFootballChampionships()`() = testScope.runTest {
        // arrange
        val response = JsonReaderUtils.getJsonFromPath(VALID_CHAMPIONSHIPS_LIST_PATH)
        mockWebServerRule.server.setResponseAndEnqueue(response)

        // act
        val result = remoteDataSource.getAllChampionsShips()

        // assert
        assert(result.isSuccess)
        assert(result.getOrNull()!!.leagues.isNotEmpty())

    }

    @Test
    fun `When server returns valid response for getChampionshipByName()`() = testScope.runTest {
        // arrange
        val response = JsonReaderUtils.getJsonFromPath(VALID_FOOTBALL_TEAMS_LIST_PATH)
        mockWebServerRule.server.setResponseAndEnqueue(response)

        // act
        val result =
            remoteDataSource.getFootballTeamsFromChampionShip(championShip = "French Ligue 1")

        // assert
        assert(result.isSuccess)
        assert(result.getOrNull()!!.teams.isNotEmpty())
    }
    // endregion Success

    // region Failure
    @Test
    fun `When server returns invalid response for getFootballChampionships()`() =
        testScope.runTest {
            // arrange
            val response = JsonReaderUtils.getJsonFromPath(INVALID_CHAMPIONSHIPS_LIST_PATH)
            mockWebServerRule.server.setResponseAndEnqueue(response)

            // act
            val result =
                remoteDataSource.getAllChampionsShips()

            // assert
            assert(result.isFailure)
        }

    @Test
    fun `When server returns invalid response for getChampionshipByName()`() = testScope.runTest {
        // arrange
        val response = JsonReaderUtils.getJsonFromPath(INVALID_FOOTBALL_TEAMS_LIST_PATH)
        mockWebServerRule.server.setResponseAndEnqueue(response)

        // act
        val result =
            remoteDataSource.getFootballTeamsFromChampionShip(championShip = "French Ligue 1")

        // assert
        assert(result.isFailure)
    }
    // endregion Failure
}