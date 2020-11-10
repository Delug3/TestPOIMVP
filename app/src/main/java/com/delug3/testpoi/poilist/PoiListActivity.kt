package com.delug3.testpoi.poilist

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.delug3.testpoi.adapter.PoisAdapter
import com.delug3.testpoi.database.PoiViewModel
import com.delug3.testpoi.database.entity.PoiRoom
import com.delug3.testpoi.databinding.ActivityMainPoisBinding
import com.delug3.testpoi.model.Poi
import com.delug3.testpoi.poidetails.PoiDetailsActivity


class PoiListActivity : AppCompatActivity(), PoiListContract.View, PoiItemClickListener {
    private lateinit var poiViewModel: PoiViewModel
    private var poisAdapter: PoisAdapter? = null
    var poiList: MutableList<Poi?> = ArrayList()
    private var poiListPresenter: PoiListPresenter? = null
    lateinit var binding: ActivityMainPoisBinding
    //val recyclerPoi: RecyclerView = binding.recyclerViewPois

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainPoisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initializing Presenter
        poiListPresenter = PoiListPresenter(this)
        // Get a new or existing ViewModel from the ViewModelProvider.
        poiViewModel = ViewModelProvider(this).get(PoiViewModel::class.java)

        setUpSearchView()
        setUpRecyclerView()
        loadData()
    }

    /**
     * setting up search view, in order to filter POIs results
     */
    private fun setUpSearchView() {
        binding.searchViewPoi.queryHint = "Look for POIs..."
        binding.searchViewPoi.setIconifiedByDefault(false)
        binding.searchViewPoi.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            //filter results every time that something changes
            override fun onQueryTextChange(query: String): Boolean {
                poisAdapter?.filter?.filter(query)
                return false
            }
        })
    }

    /**
     * setting up recycler, linear->grid
     */
    private fun setUpRecyclerView() {
        poisAdapter = PoisAdapter(this, poiList)
        binding.recyclerViewPois.adapter = poisAdapter
        binding.recyclerViewPois.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewPois.layoutManager = layoutManager
    }

    override fun showProgress() {}

    override fun hideProgress() {}

    override fun sendDataToRecyclerView(poiOnlineList: List<Poi?>?) {
        //poiList.addAll(poiOnlineList!!)
        poiOnlineList?.let { poiList.addAll(it) }
        poisAdapter!!.notifyDataSetChanged()
    }


    override fun sendDataToRoomDataBase(poiOfflineList: List<PoiRoom?>?) {
        poiViewModel.insertAllPois(poiOfflineList)
    }

    override fun updateFieldInRoomDataBase(idPoi: String, address: String?, transport: String?, email: String?, description: String?) {
        poiViewModel.updatePoi(idPoi, address, transport, email, description)
    }


    /**
     * method that obtain id of POI every time that the user click on an item
     * @param position: item position
     */
    override fun onPoiItemClick(position: Int) {
        val poi = poisAdapter!!.getFilteredItem(position)
        val idPoi = poi?.id
        val connection = isInternetAvailable(this)
        if (connection) {
            idPoi?.let { poiListPresenter!!.requestDataDetails(it) }
        } else {
            idPoi?.let { readDataDetailsFromDataBase(it) }
        }
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
    private fun loadData() {
        val connection = isInternetAvailable(this)
        if (connection) {
            poiListPresenter?.requestAllDataFromUrl()
        } else {
            //poiListPresenter!!.requestDataFromDataBase()
            readAllDataFromDataBase()
        }
    }


    private fun readAllDataFromDataBase() {
        poiViewModel.allPois.observe(this, Observer { pois ->
            // Update the cached copy of the words in the adapter.
            pois?.let { poisAdapter?.setPois(it) }
        })
    }

    private fun readDataDetailsFromDataBase(idPoi: String) {
        poiViewModel.readSinglePoi(idPoi).observe(this, { singlePoiRoom -> readPoi(singlePoiRoom) })
    }

    private fun readPoi(singlePoiRoom: List<PoiRoom?>?) {
        if (singlePoiRoom != null) {
            for (singlePoi in singlePoiRoom) {
                val title = singlePoi?.title
                val address = singlePoi?.address
                val transport = singlePoi?.transport
                val email = singlePoi?.email
                val geocoordinates = singlePoi?.geocoordinates
                val description = singlePoi?.description

                showPoiDetails(title, address, transport, email, geocoordinates, description)
            }
        }
    }


    private fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                    connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
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