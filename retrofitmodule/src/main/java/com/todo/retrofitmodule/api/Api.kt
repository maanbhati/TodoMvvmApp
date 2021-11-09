package com.todo.retrofitmodule.api

import com.todo.retrofitmodule.dto.ItemResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("v2")
    suspend fun getItems(
        @Query("q") query: String,
        @Query("app_id") appId: String = APP_ID,
        @Query("app_key") appKey: String = APP_KEY,
        @Query("type") type: String = PUBLIC
    ): Response<ItemResponse>

    companion object {
        const val PUBLIC = "public"
        const val APP_ID = "6d3cc5cd"
        const val APP_KEY = "4dee169699c65f63f3f696571652b349"
        const val BASE_URL = "https://api.edamam.com/api/recipes/"
    }
}