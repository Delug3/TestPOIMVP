package com.delug3.testpoi.poilist

import com.delug3.testpoi.database.entity.PoiRoom
import com.delug3.testpoi.model.Poi
import com.delug3.testpoi.poilist.PoiListContract.Model.OnFinishedListener
import com.delug3.testpoi.poilist.PoiListContract.Presenter

class PoiListPresenter(private var poiListView: PoiListContract.View?) : Presenter, OnFinishedListener {
    private val poiListModel: PoiListContract.Model
    override fun onDestroy() {
        poiListView = null
    }

    override fun requestAllDataFromUrl() {
        poiListView?.showProgress()
        poiListModel.getOnlineData(this)
    }

    override fun requestDataDetails(idPoi: String) {
        poiListView?.showProgress()
        poiListModel.getPoiDetails(this, idPoi)
    }

    override fun requestDataFromDataBase() {
        poiListView?.showProgress()
        poiListModel.getOfflineData(this)
    }

    override fun onFinished(poiList: List<Poi?>?, mappedPoiRoom: List<PoiRoom?>?) {
        poiListView?.sendDataToRecyclerView(poiList)
        poiListView?.sendDataToRoomDataBase(mappedPoiRoom)
        poiListView?.hideProgress()
    }

    override fun onDetailsFinished(idPoi: String, title: String?, address: String?, transport: String?, email: String?, geocoordinates: String?, description: String?) {
        poiListView?.updateFieldInRoomDataBase(idPoi, address, transport, email, description)
        poiListView?.showPoiDetails(title, address, transport, email, geocoordinates, description)
        poiListView?.hideProgress()
    }

    override fun onFailure(t: Throwable?) {
        poiListView?.onResponseFailure(t)
        poiListView?.hideProgress()
    }

    init {
        poiListModel = PoiListModel()
    }
}