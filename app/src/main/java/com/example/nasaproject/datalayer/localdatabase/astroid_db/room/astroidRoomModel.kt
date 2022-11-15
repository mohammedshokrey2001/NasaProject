package com.example.nasaproject.datalayer.localdatabase.astroid_db.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nasaproject.domain.AsteroidAppData

@Entity(tableName = "astroid_table")
data class AstroidRoomModel constructor(
   @PrimaryKey val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean

    )



fun List<AstroidRoomModel>.asDomainModel() :List<AsteroidAppData> {

 return map{
  AsteroidAppData(
   id = it.id,
   codename = it.codename,
   closeApproachDate = it.closeApproachDate,
   absoluteMagnitude = it.absoluteMagnitude,
   estimatedDiameter = it.estimatedDiameter,
   relativeVelocity = it.relativeVelocity,
   distanceFromEarth = it.distanceFromEarth,
   isPotentiallyHazardous = it.isPotentiallyHazardous
  )
 }
}