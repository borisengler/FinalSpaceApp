package com.example.finalfinalspace.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.finalfinalspace.data.db.models.CharactersInfo

@Dao
interface CharactersDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharactersInfo>): List<Long>

    @Query("SELECT * FROM characters WHERE name = :name")
    @Transaction
    suspend fun fetchCharacterByName(name: String): CharactersInfo
}
