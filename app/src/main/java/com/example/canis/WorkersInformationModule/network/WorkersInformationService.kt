package com.example.canis.WorkersInformationModule.network

import com.example.canis.WorkersInformationModule.model.Worker
import retrofit2.http.GET
import retrofit2.http.Path

interface WorkersInformationService {

    @GET("/api/workers/")
    suspend fun workerInformation(@Path("id") id:Int) : Worker

}