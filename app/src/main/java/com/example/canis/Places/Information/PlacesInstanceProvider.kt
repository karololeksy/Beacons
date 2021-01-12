package com.example.canis.Places.Information

import com.example.canis.Places.Information.data.RestPlacesProvider
import com.example.canis.Places.Information.model.PlacesService
import com.example.canis.Places.Information.network.PlacesListService
import com.example.canis.RestServiceBuilder

object PlacesInstanceProvider {

    fun getPlacesServiceInstance(): PlacesService {
        val rest = RestServiceBuilder.build(PlacesListService::class.java)
        val dataProvider = RestPlacesProvider(rest)
        val service = PlacesService(dataProvider)
        return service
    }

}