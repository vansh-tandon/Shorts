package io.project.shorts.api

import io.project.shorts.models.current.VideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PostAPI {

    @GET("videos")
    suspend fun getVideos(
        @Query("page")
        pageNumber: Int = 0
    ): VideoResponse
}