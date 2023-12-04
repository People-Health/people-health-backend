package com.peoplehealth.controller

class CoordsController {
    private val coordsList = mutableListOf<Coords>()

    fun addCoords(coords: String) {
        val (latitude, longitude) = coords.split(",")
        coordsList.add(Coords(latitude.toDouble(), longitude.toDouble()))
    }

    fun getCoords() = coordsList
}

data class Coords(val latitude: Double, val longitude: Double)