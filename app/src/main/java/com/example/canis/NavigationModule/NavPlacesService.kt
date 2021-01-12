package com.example.canis.NavigationModule

import com.example.canis.WorkersInformationModule.model.Worker
import retrofit2.http.GET
import retrofit2.http.Path

interface NavPlacesService {

    @GET("/api/navplaces/")
    suspend fun workerInformation() : List<NavPlace>

}