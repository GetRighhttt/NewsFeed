package com.example.newsfeed.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsfeed.data.model.NewsResponse
import com.example.newsfeed.data.util.Resource
import com.example.newsfeed.domain.usecase.GetNewsHeadlines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
View Model class where we demonstrate how to properly incorporate state components into our
view model layer. Have to extend AndroidViewModel in order to pass our application context for
state of the internet purposes.
 */
class NewsViewModel(
    private val getNewsHeadlines: GetNewsHeadlines, private val app: Application
) : AndroidViewModel(app) {
    private val newsHeadlines: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    /*
    Use coroutines to launch a job to get the news headlines in a background thread.

    If loading or in an error state, display a message.
     */
    fun getNewsHeadLines(country: String, page: Int) = viewModelScope.launch(Dispatchers.IO) {
        newsHeadlines.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val apiResult = getNewsHeadlines.execute(country, page)
                newsHeadlines.postValue(apiResult)
            } else {
                newsHeadlines.postValue(Resource.Error("Internet is not available."))
            }
        } catch (e: Exception) {
            newsHeadlines.postValue(Resource.Error(e.message.toString()))
        }
    }

    /*
    Must prepare for all situations, so this function will serve the purpose of
    checking the internet availability.

    Pretty generic android code. Can reuse method for other projects also.
     */
    private fun isNetworkAvailable(context: Context?): Boolean {
        if(context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager
                .getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}