package com.example.finalfinalspace.datamanagment.characters

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CharactersDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharactersInfo>): List<Long>

    @Query("SELECT * from characters WHERE id in (:id)")
    fun getCharacters(id: List<Int>): Flow<List<CharactersInfo>>

    @Query("SELECT * from characters ORDER BY id ASC")
    fun getAllCharacters(): Flow<List<CharactersInfo>>
}