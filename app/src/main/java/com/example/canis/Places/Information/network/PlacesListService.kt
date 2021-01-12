package com.example.canis.Places.Information.network

import com.example.canis.Places.Information.model.Place
import retrofit2.http.GET

interface PlacesListService {

    @GET("/api/places/")
    suspend fun listOfWorkers(): List<Place>

}