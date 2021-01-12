package com.example.canis.Places.Information

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.canis.Places.Information.model.Place
import com.example.canis.R
import kotlinx.android.synthetic.main.place_details_item.view.*
import kotlinx.android.synthetic.main.place_item.view.*

class PlacesAdapter(private val listener: (Place) -> Unit): RecyclerView.Adapter<PlacesAdapter.PlacesHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        return PlacesHolder(inflatedView, listener)
    }

    val placesList = mutableListOf<Place>()

    fun addList(listToAdd: List<Place>){
        placesList.addAll(listToAdd)
    }

    override fun getItemCount(): Int {
        return placesList.size
    }

    override fun onBindViewHolder(holder: PlacesHolder, position: Int) {
        val item = placesList[position]
        holder.bind(item)
    }

    class PlacesHolder(private val view: View, private val itemClick: (Place) -> Unit):RecyclerView.ViewHolder(view){
        fun bind(place: Place){
            view.placeName.text = place.name
            Glide.with(view).load(place.photo).placeholder(R.drawable.photo_placeholder).into(view.placeImage)
            view.setOnClickListener {
                itemClick(place)
            }
        }
    }

}