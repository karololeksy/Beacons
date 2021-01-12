package com.example.canis.Places


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.canis.BeaconModule.Repeater
import com.example.canis.NavigationWorkersModule.MapboxWorkers
import com.example.canis.Places.Information.model.Beacon
import com.example.canis.Places.Information.model.Navplace
import com.example.canis.Places.Information.model.Place

import com.example.canis.R
import kotlinx.android.synthetic.main.fragment_places.*
import kotlinx.android.synthetic.main.fragment_places_details.*
import kotlinx.android.synthetic.main.fragment_places_details.placesRecyclerView

/**
 * A simple [Fragment] subclass.
 */
class PlacesDetailsFragment(private val place: Place) : Fragment() {

    private val adapter = PlacesDetailsAdapter{onItemClicked(it)}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_places_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        placesRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter.addList(place.navplaces)
        placesRecyclerView.adapter = adapter

    }

    private fun onItemClicked(place: Navplace){
        val navigationIntent = Intent(this.context, MapboxWorkers::class.java)

        for (item: Beacon in place.beacons) {
            Repeater.getBeacons().add(item.id)
        }

        Repeater.setRoom(place.room)

        val cordsBundle = Bundle()
        cordsBundle.putDouble("latWorker",place.lat)
        cordsBundle.putDouble("lonWorker",place.lon)
        cordsBundle.putString("workerPlace", place.name)
        cordsBundle.putBoolean("isWorker",true)

        navigationIntent.putExtras(cordsBundle)

        startActivity(navigationIntent)
    }

    companion object{
        @JvmStatic
        fun newInstance(place: Place): PlacesDetailsFragment{
            return PlacesDetailsFragment(place)
        }
    }


}
