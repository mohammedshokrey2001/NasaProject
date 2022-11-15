package com.example.nasaproject.datalayer.localdatabase.astroid_db.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AstroidDao {

    //  get live data about all astorids today
    @Query("SELECT * FROM astroid_table WHERE closeApproachDate = date('now') order by closeApproachDate asc")
    fun getTodayAsteroids(): LiveData<List<AstroidRoomModel>>

    //  get live data about all astorids through this week
    @Query("SELECT * FROM astroid_table WHERE closeApproachDate BETWEEN date('now') AND date('now', '+7 day') order by closeApproachDate asc")
     fun getWeeklyAsteroids(): LiveData<List<AstroidRoomModel>>

    @Query("select * from astroid_table  order by closeApproachDate asc")
    fun getAsteroids(): LiveData<List<AstroidRoomModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend  fun insertAll(vararg asteroids: AstroidRoomModel)


    @Query("delete from astroid_table where closeApproachDate < :today")
   suspend fun clearOldAsteroids(today: String)


}