package io.project.shorts.repository

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.project.shorts.api.RetrofitInstance
import io.project.shorts.models.current.VideoResponse
import retrofit2.Response
@InstallIn(SingletonComponent::class)
@Module
class Repository {
    suspend fun getVideos(pageNumber: Int): Response<VideoResponse>{
        return RetrofitInstance.api.getVideos(pageNumber)
    }
}