package com.delug3.testpoi.database


import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.delug3.testpoi.database.dao.PoiDao
import com.delug3.testpoi.database.entity.PoiRoom


class PoiRepository(private val poiDao: PoiDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allPois: LiveData<List<PoiRoom>> = poiDao.getAllPois()


    // You must call this on a non-UI thread or your app will crash. So we're making this a
    // suspend function so the caller methods know this.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(poiRoom: PoiRoom) {
        poiDao.insert(poiRoom)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAllPois(poisRoomList: List<PoiRoom?>?) {
        poiDao.insertAllPois(poisRoomList)
    }


}