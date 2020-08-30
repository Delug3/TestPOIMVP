package com.delug3.testpoi.database.poirepository


import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

import com.delug3.testpoi.database.PoiDatabase
import com.delug3.testpoi.database.dao.PoiDao
import com.delug3.testpoi.database.entity.PoiRoom
import io.reactivex.Observable


class PoiRepository(application: Application) {


    private lateinit var poiDao: PoiDao
    private var allPois: LiveData<List<PoiRoom>>

    init {
        val database: PoiDatabase? = PoiDatabase.getInstance(application)
        if (database != null) {
            poiDao = database.poiDao()
        }
       allPois = poiDao.getAllPois()
    }
    fun insert(poiRoom: PoiRoom){
        val insertPoiAsyncTask = InsertPoiAsyncTask(poiDao).execute(poiRoom)
    }

    fun getAllPois(): LiveData<List<PoiRoom>>{
        return allPois
    }
    private class InsertPoiAsyncTask(poiDao: PoiDao) : AsyncTask<PoiRoom, Unit, Unit>() {
        val poiDao = poiDao

        override fun doInBackground(vararg p0: PoiRoom?) {
         poiDao.insert(p0[0]!!)
        }
    }

}