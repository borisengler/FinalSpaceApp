package com.example.finalfinalspace.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.finalfinalspace.data.db.models.EpisodeWithCharactersInfo
import com.example.finalfinalspace.data.db.models.EpisodesInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface EpisodesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(episodes: List<EpisodesInfo>): List<Long>

    @Query("SELECT * from episodes ORDER BY episodeId ASC")
    fun getAllEpisodes(): Flow<List<EpisodesInfo>>

    @Transaction
    @Query("SELECT * FROM episodes WHERE episodeId = :id")
    suspend fun fetchEpisodeWithCharacters(id: Int): EpisodeWithCharactersInfo
}
