package com.p4r4d0x.edmgenreclassifier.datasource.retrofit

import com.p4r4d0x.edmgenreclassifier.datasource.dto.GenreClassifyRequestDTO
import com.p4r4d0x.edmgenreclassifier.datasource.dto.GenreClassifyResponseDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ClassifyRetrofitService {
    @POST("dev/classifier/classifygenre")
    fun classifySong(@Body classifyRequest: GenreClassifyRequestDTO?): Call<GenreClassifyResponseDTO?>?
}