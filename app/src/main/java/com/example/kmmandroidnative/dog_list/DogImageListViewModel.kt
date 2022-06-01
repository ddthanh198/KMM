package com.example.kmmandroidnative.dog_list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmm_module.remote.BaseApiClass
import kotlinx.coroutines.launch

class DogImageListViewModel : ViewModel() {
    var apiService = BaseApiClass()
    val listDogImageUrl = MutableLiveData<List<String>>()

    fun getDogImageUrlList(breedName : String) {
        viewModelScope.launch {
            apiService.getDogImageUrlList(breedName).fold(
                failed = {
                    Log.d("dLog", "DogImageListViewModel getDogImageUrlList: failed $it")
                },
                succeeded = {
                    Log.d("dLog", "DogImageListViewModel getDogImageUrlList: succeeded $breedName")
                    listDogImageUrl.postValue(it.message)
                }
            )
        }
    }
}