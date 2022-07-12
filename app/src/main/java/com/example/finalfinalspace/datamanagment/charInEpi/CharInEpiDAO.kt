package com.example.finalfinalspace.datamanagment.charInEpi

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CharInEpiDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(charInEpi: CharInEpiInfo): Long

    @Query("SELECT * from char_in_episode WHERE episode_id = :episode_id")
    suspend fun fetchCharactersInEpisode(episode_id: Int): List<CharInEpiInfo>

    @Query("SELECT * FROM char_in_episode")
    fun getCharactersInEpisodes(): Flow<List<CharInEpiInfo>>

}