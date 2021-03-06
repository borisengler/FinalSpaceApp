package com.example.finalfinalspace.datamanagment.episodes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EpisodesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(episodes: EpisodesInfo): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(episodes: List<EpisodesInfo>): List<Long>

    @Query("SELECT * from episodes WHERE id = :id")
    suspend fun fetchEpisode(id: Int): EpisodesInfo

    @Query("SELECT * from episodes WHERE id = :id")
    fun getEpisode(id: Int): Flow<EpisodesInfo>

    @Query("SELECT * from episodes ORDER BY id ASC")
    fun getAllEpisodes(): Flow<List<EpisodesInfo>>
}