package com.delug3.testpoi.poilist

import com.delug3.testpoi.database.entity.PoiRoom
import com.delug3.testpoi.model.Poi

/**
 * PoiList is a contract between view and presenter of MVP
 */
interface PoiListContract {
    interface Model {
        interface OnFinishedListener {
            fun onFinished(poiList: List<Poi?>?, mappedPoiRoom: List<PoiRoom?>?)
            fun onDetailsFinished(idPoi: String, title: String?, address: String?, transport: String?, email: String?, geocoordinates: String?, description: String?)
            fun onFailure(t: Throwable?)
        }

        fun getOnlineData(onFinishedListener: OnFinishedListener?)
        fun getPoiDetails(onFinishedListener: OnFinishedListener?, idPoi: String)
        fun getOfflineData(onFinishedListener: OnFinishedListener?)
    }

    interface View {
        fun showProgress()
        fun hideProgress()
        fun sendDataToRecyclerView(poiOnlineList: List<Poi?>?)
        fun sendDataToRoomDataBase(poiOfflineList: List<PoiRoom?>?)
        fun updateFieldInRoomDataBase(idPoi: String, address: String?, transport: String?, email: String?, description: String?)
        fun showPoiDetails(title: String?, address: String?, transport: String?, email: String?, geocoordinates: String?, description: String?)
        fun onResponseFailure(throwable: Throwable?)
    }

    interface Presenter {
        fun onDestroy()
        fun requestAllDataFromUrl()
        fun requestDataDetails(idPoi: String)
        fun requestDataFromDataBase()
    }
}