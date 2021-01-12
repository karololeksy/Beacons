package com.example.canis.NavigationModule

import com.example.canis.RestServiceBuilder


object NavigationInstanceProvider {

    fun getNavPlacesServiceInstance(): PlacesService {
        val rest1 = RestServiceBuilder.build(NavPlacesService::class.java)

        val dataProvider = RestNavPlacesDataProvider(rest1)

        val service = PlacesService(dataProvider)

        return service
    }

}