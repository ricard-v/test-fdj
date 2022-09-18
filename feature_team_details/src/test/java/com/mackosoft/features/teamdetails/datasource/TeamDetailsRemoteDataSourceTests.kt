package com.mackosoft.features.teamdetails.datasource

import com.mackosoft.core.base.datasource.api.RetrofitClient
import com.mackosoft.core.test.base.network.BaseRemoteDataSourceTests
import com.mackosoft.core.test.base.network.assertPathMatch
import com.mackosoft.core.test.base.network.setResponseAndEnqueue
import com.mackosoft.core.test.utils.JsonReaderUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TeamDetailsRemoteDataSourceTests : BaseRemoteDataSourceTests<TeamDetailsRemoteDataSource>() {

    private companion object {
        const val INVALID_FOOTBALL_TEAMS_LIST_PATH =
            "json/failure/invalid_football_teams_data.json"
        const val VALID_FOOTBALL_TEAMS_LIST_PATH =
            "json/success/valid_football_teams_data.json"
    }

    override val remoteDataSource: TeamDetailsRemoteDataSource =
        TeamDetailsRemoteDataSource(RetrofitClient)

    @Test
    fun `When server returns valid response for getFootballTeamDetails()`() = testScope.runTest {
        // arrange
        val tTeamId = "1"
        val response = JsonReaderUtils.getJsonFromPath(VALID_FOOTBALL_TEAMS_LIST_PATH)
        mockWebServerRule.server.setResponseAndEnqueue(response)

        // act
        val result = remoteDataSource.getFootballTeamDetails(id = tTeamId)

        // assert
        assert(result.isSuccess)
        assert(result.getOrNull()!!.teams.isNotEmpty() && result.getOrNull()!!.teams.size == 1)
        mockWebServerRule.server.assertPathMatch("/lookupteam.php?id=$tTeamId")
    }

    @Test
    fun `When server returns invalid response for getFootballTeamDetails()`() = testScope.runTest {
        // arrange
        val tTeamId = "1"
        val response = JsonReaderUtils.getJsonFromPath(INVALID_FOOTBALL_TEAMS_LIST_PATH)
        mockWebServerRule.server.setResponseAndEnqueue(response)

        // act
        val result = remoteDataSource.getFootballTeamDetails(id = tTeamId)

        // assert
        assert(result.isFailure)
    }
}