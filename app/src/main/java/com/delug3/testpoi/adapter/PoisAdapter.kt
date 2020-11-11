package com.delug3.testpoi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.delug3.testpoi.database.entity.PoiRoom
import com.delug3.testpoi.databinding.ItemPoisBinding
import com.delug3.testpoi.model.Poi
import com.delug3.testpoi.poilist.PoiListActivity
import java.util.*

class PoisAdapter(private val poiListActivity: PoiListActivity, private val poiList: MutableList<Poi?>) : RecyclerView.Adapter<PoisAdapter.ViewHolder>(), Filterable {

    private var poiListFiltered: List<Poi?>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPoisBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val poi = poiListFiltered[position]
        holder.bind(poi)
        holder.itemView.setOnClickListener { poiListActivity.onPoiItemClick(position) }
    }

    internal fun setPois(poisFromDataBase: List<PoiRoom>) {

        val mappedRoomList = poisFromDataBase.map { Poi(it.id, it.title, it.address, it.transport, it.email, it.geocoordinates, it.description) }

        this.poiListFiltered = mappedRoomList
        notifyDataSetChanged()
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
                    val filterPattern = charSequence.toString().toLowerCase(Locale.ROOT).trim { it <= ' ' }
                    for (poi in poiList) {
                        if (poi?.title!!.toLowerCase(Locale.ROOT).contains(filterPattern)) {
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

    class ViewHolder(val binding: ItemPoisBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(poi: Poi?) {
            binding.textViewTitle.text = poi?.title
            binding.textViewGeocoordinates.text = poi?.geocoordinates
        }
    }

    init {
        poiListFiltered = poiList
    }
}