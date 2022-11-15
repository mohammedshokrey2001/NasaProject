package com.example.nasaproject.datalayer.localdatabase.picture_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nasaproject.domain.PictureOfDay

@Dao
interface PictureOfDayDao {
    // Picture of the day
    @Query("select * from picture_of_the_day_table order by created_at desc limit 1")
    fun getPictureOfDay(): LiveData<PictureOfDayModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPictureOfDay(pictureOfDay: PictureOfDayModel)


    @Query("delete from picture_of_the_day_table where created_at < :today")
    fun clearOldPictureOfDay(today: String)

}