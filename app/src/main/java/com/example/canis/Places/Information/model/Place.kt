package com.example.canis.Places.Information.model

data class Place(
    val id: Int,
    val name: String,
    val navplaces: List<Navplace>,
    val photo: String
)