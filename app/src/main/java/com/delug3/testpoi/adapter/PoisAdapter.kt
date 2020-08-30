package com.delug3.testpoi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.delug3.testpoi.R
import com.delug3.testpoi.model.Poi
import com.delug3.testpoi.poilist.PoiItemClickListener
import com.delug3.testpoi.poilist.PoiListActivity
import java.util.*

class PoisAdapter(private val poiListActivity: PoiListActivity, private val poiList: MutableList<Poi?>) : RecyclerView.Adapter<PoisAdapter.ViewHolder>(), Filterable {

    private var poiListFiltered: MutableList<Poi?>
    private val poiItemClickListener: PoiItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pois, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val poi = poiListFiltered[position]
        holder.textViewTitle.text = poi?.title
        holder.textViewGeocoordinates.text = poi?.geocoordinates
        holder.itemView.setOnClickListener { poiListActivity.onPoiItemClick(position) }
    }

    override fun getItemCount(): Int {
        return poiListFiltered.size
    }

    fun getFilteredItem(id: Int): Poi? {
        return poiListFiltered[id]
    }

    //filter method to obtain items with the same sequence
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    poiListFiltered = poiList
                } else {
                    val filteredList: MutableList<Poi?> = ArrayList()
                    val filterPattern = charSequence.toString().toLowerCase().trim { it <= ' ' }
                    for (poi in poiList) {
                        if (poi?.title!!.toLowerCase().contains(filterPattern)) {
                            filteredList.add(poi)
                        }
                    }
                    poiListFiltered = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = poiListFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                poiListFiltered = filterResults.values as MutableList<Poi?>
                notifyDataSetChanged()
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView
        val textViewGeocoordinates: TextView

        init {
            textViewTitle = itemView.findViewById(R.id.text_view_title)
            textViewGeocoordinates = itemView.findViewById(R.id.text_view_address)
        }
    }

    init {
        poiListFiltered = poiList
    }
}