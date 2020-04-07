package com.p4r4d0x.edmgenreclassifier.model

import com.google.gson.annotations.SerializedName


data class GenreClassifierDTO(
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