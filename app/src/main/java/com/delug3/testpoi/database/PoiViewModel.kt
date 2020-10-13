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
    //val singlePoi: LiveData<List<PoiRoom>>


    init {
        val poiDao = PoiRoomDatabase.getDatabase(application, viewModelScope).poiDao()
        repository = PoiRepository(poiDao)
        allPois = repository.allPois
        //singlePoi = repository.singlePoi
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

    fun updatePoi(id: String, address: String?, transport: String?, email: String?, description: String?) = viewModelScope.launch(Dispatchers.IO) {
        repository.updatePoi(id, address, transport, email, description)
    }

    /*fun readSinglePoi(id: String) = viewModelScope.launch(Dispatchers.IO){
        repository.readSinglePoi(id)
    }

     */

    fun readSinglePoi(id: String): LiveData<List<PoiRoom>> {
        return repository.readSinglePoi(id)
    }


}