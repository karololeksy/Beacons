package com.example.canis.WorkersInformationModule.network

import com.example.canis.WorkersInformationModule.model.Worker
import retrofit2.http.GET

interface WorkersListService {

    @GET("/api/workers/")
    suspend fun listOfWorkers(): List<Worker>


}