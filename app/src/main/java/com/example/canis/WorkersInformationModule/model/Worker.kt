package com.example.canis.WorkersInformationModule.model

data class Worker(
    val address: String,
    val contact: String,
    val firstName: String,
    val hours: String,
    val id: Int,
    val image: String,
    val lastName: String,
    val lat: Double,
    val lon: Double,
    val roads: List<Any>
)