package com.example.nasaproject.datalayer.api

import com.example.nasaproject.datalayer.localdatabase.astroid_db.room.AstroidRoomModel


data class AstroidNetworkModel   (val id: Long, val codename: String, val closeApproachDate: String,
                                                     val absoluteMagnitude: Double, val estimatedDiameter: Double,
                                                     val relativeVelocity: Double, val distanceFromEarth: Double,
                                 val isPotentiallyHazardous: Boolean)



fun ArrayList<AstroidNetworkModel>.asDatabaseModel(): Array<AstroidRoomModel> {
    return map {
        AstroidRoomModel(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }.toTypedArray()
}