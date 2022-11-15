package com.example.nasaproject.datalayer.api

import com.example.nasaproject.datalayer.localdatabase.picture_db.PictureOfDayModel
import com.squareup.moshi.Json

data class PictureOfTheDayNetwork(@Json(name = "media_type")val mediaType: String, val title: String,
                                  val url: String)




fun PictureOfTheDayNetwork.asDatabaseModel(): PictureOfDayModel {
    return PictureOfDayModel(
        mediaType = this.mediaType,
        title = this.title,
        url = this.url,
        createdAt = System.currentTimeMillis())

}