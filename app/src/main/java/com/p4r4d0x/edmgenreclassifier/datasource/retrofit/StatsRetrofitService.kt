package com.p4r4d0x.edmgenreclassifier.datasource.retrofit

import com.p4r4d0x.edmgenreclassifier.datasource.dto.UserStatsDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface StatsRetrofitService {
    @GET("dev/classifier/stats")
    fun userStats(@Query("userId") userId: Long?): Call<UserStatsDTO?>?
}