package com.example.finalfinalspace.datamanagment.charInEpi

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharInEpiDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(charInEpi: CharInEpiInfo): Long

    @Query("SELECT * from char_in_episode WHERE episode_id = :episode_id")
    fun getCharactersInEpisode(episode_id: Int): List<CharInEpiInfo>

}