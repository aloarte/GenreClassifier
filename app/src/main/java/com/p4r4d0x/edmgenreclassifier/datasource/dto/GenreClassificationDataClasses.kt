package com.p4r4d0x.edmgenreclassifier.datasource.dto

import com.google.gson.annotations.SerializedName


data class GenreClassifyResponseDTO(
    @SerializedName("classifyResponse")
    val classifyResponse: ClassifyResponse
)

data class ClassifyResponse(
    @SerializedName("genres")
    val genres: List<GenresItem>?,
    @SerializedName("genre")
    val genre: String = "",
    @SerializedName("songDetail")
    val songDetail: SongDetail
)

data class GenresItem(
    @SerializedName("songGenre")
    val songGenre: String = "",
    @SerializedName("precision")
    val precision: Double = 0.0
)

data class SongDetail(
    @SerializedName("songName")
    val songName: String = "",
    @SerializedName("danceability")
    val danceability: Boolean = false,
    @SerializedName("bpm")
    val bpm: Int = 0,
    @SerializedName("singableRate")
    val singableRate: Double = 0.0
)

data class User(
    @SerializedName("clientId")
    val clientId: String = "",
    @SerializedName("userLogged")
    val userLogged: Boolean = false
)


data class SongInfo(
    @SerializedName("songName")
    val songName: String = "",
    @SerializedName("songData")
    val songData: String = "",
    @SerializedName("sampleType")
    val sampleType: String = "",
    @SerializedName("songFormat")
    val songFormat: String = "",
    @SerializedName("sampleTime")
    val sampleTime: Int = 0
)


data class GenreClassifyRequestDTO(
    @SerializedName("date")
    val date: Int = 0,
    @SerializedName("accuracy")
    val accuracy: Double = 0.0,
    @SerializedName("songInfo")
    val songInfo: SongInfo,
    @SerializedName("user")
    val user: User
)
