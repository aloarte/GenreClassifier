package com.p4r4d0x.edmgenreclassifier.datasource.dto

import com.google.gson.annotations.SerializedName

data class UserStatsDTO(
    @SerializedName("statsResponse")
    val statsResponse: StatsResponse
)


data class StatsResponse(
    @SerializedName("relatedGenre")
    val relatedGenre: List<String>?,
    @SerializedName("lastClassify")
    val lastClassify: LastClassify,
    @SerializedName("classifiyStats")
    val classifyStats: ClassifyStats,
    @SerializedName("relatedSongName")
    val relatedSongName: List<String>?,
    @SerializedName("userId")
    val userId: Long = 0
)

data class LastClassify(
    @SerializedName("lastSampleTime")
    val lastSampleTime: Int = 0,
    @SerializedName("lastAudioType")
    val lastAudioType: String = "",
    @SerializedName("lastGenre")
    val lastGenre: String = ""
)

data class ClassifyStats(
    @SerializedName("consecutiveGenre")
    val consecutiveGenre: String = "",
    @SerializedName("consecutiveAudioType")
    val consecutiveAudioType: String = "",
    @SerializedName("avgSampleTime")
    val avgSampleTime: Int = 0,
    @SerializedName("avgGenre")
    val avgGenre: String = "",
    @SerializedName("avgAudioType")
    val avgAudioType: String = ""
)