package com.example.nasaproject.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


//the data model witch will used in the activities
@Parcelize
data class AsteroidAppData(val id: Long, val codename: String, val closeApproachDate: String,
                           val absoluteMagnitude: Double, val estimatedDiameter: Double,
                           val relativeVelocity: Double, val distanceFromEarth: Double,
                           val isPotentiallyHazardous: Boolean) : Parcelable

