package com.example.canis.Places

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.canis.Places.Information.model.Navplace
import com.example.canis.R
import kotlinx.android.synthetic.main.place_details_item.view.*

class PlacesDetailsAdapter(private val listener: (Navplace) -> Unit): RecyclerView.Adapter<PlacesDetailsAdapter.PlacesHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.place_details_item, parent, false)
        return PlacesHolder(inflatedView, listener)
    }

    val placesList = mutableListOf<Navplace>()

    fun addList(listToAdd: List<Navplace>){
        placesList.addAll(listToAdd)
    }

    override fun getItemCount(): Int {
        return placesList.size
    }

    override fun onBindViewHolder(holder: PlacesHolder, position: Int) {
        val item = placesList[position]
        holder.bind(item)
    }

    class PlacesHolder(private val view: View, private val itemClick: (Navplace) -> Unit):RecyclerView.ViewHolder(view){
        fun bind(place: Navplace){
            view.placeDetailsName.text = place.name
            view.setOnClickListener {
                itemClick(place)
            }
        }
    }

}