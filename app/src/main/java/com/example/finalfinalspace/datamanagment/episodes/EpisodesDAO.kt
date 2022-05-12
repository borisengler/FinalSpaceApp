package com.example.finalfinalspace.datamanagment.episodes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.finalfinalspace.datamanagment.quotes.QuotesInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface EpisodesDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: EpisodesInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(episodes : List<EpisodesInfo>) : List<Long>

    @Query("SELECT * from episodes WHERE id = :id")
    fun getEpisode(id: Int): EpisodesInfo

    @Query("SELECT * from episodes ORDER BY id ASC")
    fun getAllEpisodes(): List<EpisodesInfo>
}