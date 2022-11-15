package com.example.nasaproject.datalayer.localdatabase.picture_db

import androidx.lifecycle.Transformations.map
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nasaproject.domain.PictureOfDay

@Entity(tableName = "picture_of_the_day_table")
data class PictureOfDayModel constructor(
    @PrimaryKey val url: String,

    @ColumnInfo(name = "created_at")
    val createdAt: Long,
    val title: String,
    val mediaType: String
)

fun PictureOfDayModel.asDomainModel():PictureOfDay{
    return  PictureOfDay(
        url = this.url,
        mediaType = this.mediaType,
        title = this.title
        )
    }

