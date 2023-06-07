package io.project.shorts.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.project.shorts.ShortsApplication
import io.project.shorts.models.current.Data
import io.project.shorts.models.current.VideoResponse
import io.project.shorts.repository.Repository
import io.project.shorts.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class MainViewModel(val videoRepository: Repository,
                    application: Application
): AndroidViewModel(application) {


    private var _videosResponse: MutableLiveData<Resource<VideoResponse?>?> = MutableLiveData()
    private var _handleResponse: MutableLiveData<String> = MutableLiveData()
    private var _reactionResponse: MutableLiveData<Int> = MutableLiveData()
    private var _urlResponse: MutableLiveData<String> = MutableLiveData()
    private var _thumbnailResponse: MutableLiveData<VideoResponse> = MutableLiveData()
    val handleResponse: LiveData<String> = _handleResponse
    val reactionResponse: LiveData<Int> = _reactionResponse
    val videosResponse: LiveData<Resource<VideoResponse?>?> = _videosResponse
    val urlResponse: LiveData<String> = _urlResponse
    val thumbnailResponse: LiveData<VideoResponse> = _thumbnailResponse
    var thumbnailPage = 1
    var fetchedVideoResponse : VideoResponse? = null




    fun fetchVideos() = viewModelScope.launch {
        safeFetchVideosCall()
    }

    private suspend fun safeFetchVideosCall() {
        _videosResponse.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()) {
                val response = videoRepository.getVideos(thumbnailPage)
                _videosResponse.postValue(handleThumbnailResponse(response))
            } else {
                _videosResponse.postValue(Resource.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> _videosResponse.postValue(Resource.Error("Network Failure"))
                else -> _videosResponse.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleThumbnailResponse(response: Response<VideoResponse>) : Resource<VideoResponse?> {
        if(response.isSuccessful){
            response.body()?.let {resultResponse ->
                //every time we get response we increase the pg no. by 1
                thumbnailPage++
                //if that response is the first response, snr = rr, with all available articles
                //and all the pages that we loaded yet
                if (fetchedVideoResponse == null){
                    fetchedVideoResponse = resultResponse
                }
                else{
                    //if we have loaded more than 1 page already
                    val oldArticles = fetchedVideoResponse?.data
                    val newArticles = resultResponse.data
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(searchNewsResponse?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<ShortsApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
//    private fun fetchVideos() {
//        viewModelScope.launch {
////            val response = repository.getVideos()
////            val list = response.body()?.data?.posts
//////            list?.get(1)?.submission?.mediaUrl
//            val response = repository.getVideos()
//
//            if (response.isSuccessful) {
//                val videoResponse = response.body()
//                _videosResponse?.value=videoResponse!!
//                Log.d("videosResponse",_videosResponse.toString())
//                videoResponse?.data?.posts?.let { videos ->
//                    for (video in videos) {
//                        val postId = video?.postId
//                        val creatorName = video?.creator?.name
//                        val title = video?.submission?.title
//                        _handleResponse.value = video?.creator?.handle!!
//                        _reactionResponse.value = video.reaction?.count!!
//                        _urlResponse.value = video.submission?.mediaUrl!!
//                        _thumbnailResponse.value = video.submission.thumbnail!!
//                    }
//                }

//            }
//        }
//
//    }
    init {
        fetchVideos()
    }
}
