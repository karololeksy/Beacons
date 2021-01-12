package com.example.canis.Places.Information


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.canis.Places.Information.model.Place
import com.example.canis.Places.PlacesDetailsFragment

import com.example.canis.R
import kotlinx.android.synthetic.main.fragment_places.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlacesFragment : Fragment() {

    private val service = PlacesInstanceProvider.getPlacesServiceInstance()
    val adapter = PlacesAdapter{onItemClicked(it)}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_places, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch(Dispatchers.Main) {
            val places = service.fetchPlaces()

            placesRecyclerView.layoutManager = LinearLayoutManager(context)
            adapter.addList(places)
            placesRecyclerView.adapter = adapter
        }

    }

    private fun onItemClicked(place: Place){
        val placesDetailsFragment = PlacesDetailsFragment.newInstance(place)
        fragmentManager?.let{
            it.beginTransaction()
                    .addToBackStack("worker_item")
                    .replace(R.id.placesModule,placesDetailsFragment)
                    .commit()
        }
    }

    companion object{
        @JvmStatic
        fun newInstance() = PlacesFragment()
    }

}
