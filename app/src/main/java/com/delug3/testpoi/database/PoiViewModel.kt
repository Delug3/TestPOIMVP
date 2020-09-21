package com.delug3.testpoi.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.delug3.testpoi.database.entity.PoiRoom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PoiViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PoiRepository
    // - Repository is completely separated from the UI through the ViewModel.
    val allPois: LiveData<List<PoiRoom>>

    init {
        val poiDao = PoiRoomDatabase.getDatabase(application, viewModelScope).poiDao()
        repository = PoiRepository(poiDao)
        allPois = repository.allPois
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(poiRoom: PoiRoom) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(poiRoom)
    }

    fun insertAllPois(poisRoomList: List<PoiRoom?>?) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertAllPois(poisRoomList)
    }

}