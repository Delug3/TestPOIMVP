package com.delug3.testpoi.poilist

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.delug3.testpoi.R
import com.delug3.testpoi.adapter.PoisAdapter
import com.delug3.testpoi.model.Poi
import com.delug3.testpoi.poidetails.PoiDetailsActivity
import java.util.*

class PoiListActivity : AppCompatActivity(), PoiListContract.View, PoiItemClickListener {
    private var recyclerViewPoi: RecyclerView? = null
    var searchViewPoi: SearchView? = null
    private var poisAdapter: PoisAdapter? = null
    var poiList: MutableList<Poi?> = ArrayList()
    private var poiListPresenter: PoiListPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_pois)

        //Initializing Presenter
        poiListPresenter = PoiListPresenter(this)
        initUI()
        setUpSearchView()
        setUpRecyclerView()
        checkInternetConnection()
    }

    /**
     * initialization of views, not required butterknife for now
     */
    private fun initUI() {
        recyclerViewPoi = findViewById(R.id.recyclerViewPois)
        searchViewPoi = findViewById(R.id.search_view_poi)
    }

    /**
     * setting up search view, in order to filter POIs results
     */
    private fun setUpSearchView() {
        searchViewPoi!!.queryHint = "Look for POIs..."
        searchViewPoi!!.setIconifiedByDefault(false)
        searchViewPoi!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            //filter results every time that something changes
            override fun onQueryTextChange(query: String): Boolean {
                poisAdapter!!.filter.filter(query)
                return false
            }
        })
    }

    /**
     * setting up recycler, linear->grid
     */
    private fun setUpRecyclerView() {
        poisAdapter = PoisAdapter(this, poiList)
        recyclerViewPoi!!.adapter = poisAdapter
        recyclerViewPoi!!.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        recyclerViewPoi!!.layoutManager = layoutManager
    }

    override fun showProgress() {}
    override fun hideProgress() {}

    override fun sendDataToRecyclerView(poiArrayList: List<Poi?>?) {
        poiList.addAll(poiArrayList!!)
        poisAdapter!!.notifyDataSetChanged()
    }

    /**
     * method that obtain id of POI every time that the user click on an item
     * @param position: item position
     */
    override fun onPoiItemClick(position: Int) {
        val poi = poisAdapter!!.getFilteredItem(position)
        val idPoi = poi?.id
        poiListPresenter!!.requestDataDetails(idPoi)
    }

    /**
     * method that open a new activity with the data obtained from an url
     * @param title : the Point of Interest title
     * @param address: POI address
     * @param transport: POI transport
     * @param email: POI mail
     * @param geocoordinates: POI location, the coordinates
     * @param description: more info about the POI
     */
    override fun showPoiDetails(title: String?, address: String?, transport: String?, email: String?, geocoordinates: String?, description: String?) {
        val intent = Intent(this@PoiListActivity, PoiDetailsActivity::class.java)
        intent.putExtra("POI_TITLE", title)
        intent.putExtra("POI_ADDRESS", address)
        intent.putExtra("POI_TRANSPORT", transport)
        intent.putExtra("POI_EMAIL", email)
        intent.putExtra("POI_GEOCOORDINATES", geocoordinates)
        intent.putExtra("POI_DESCRIPTION", description)
        startActivity(intent)
    }

    /**
     * checking internet connectivity,
     * if it's available, get data from api
     * if isn't available, get data from room database
     */
    private fun checkInternetConnection() {
        val connectivityManager = this.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val currentNetworkInfo = connectivityManager.activeNetworkInfo
        //improve this!!
        if (currentNetworkInfo!!.isConnected) {
            poiListPresenter!!.requestAllDataFromUrl()
        } else {
            poiListPresenter!!.requestDataFromDataBase()
        }
    }

    override fun onResponseFailure(throwable: Throwable?) {
        Log.e(TAG, throwable!!.message!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        poiListPresenter!!.onDestroy()
    }

    companion object {
        private const val TAG = "POIs"
    }
}