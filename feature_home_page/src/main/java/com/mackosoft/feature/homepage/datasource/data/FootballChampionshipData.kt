package com.mackosoft.feature.homepage.datasource.data

import com.google.gson.annotations.SerializedName

data class FootballChampionshipData(
    @SerializedName("idLeague")
    val id: String,
    @SerializedName("strLeague")
    val name: String,
)
