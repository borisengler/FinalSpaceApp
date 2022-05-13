package com.example.finalfinalspace.datamanagment.characters

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharactersDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(characters : List<CharactersInfo>) : List<Long>

    @Query("SELECT * from characters WHERE id = :id")
    fun getCharacter(id: Int): CharactersInfo

    @Query("SELECT * from characters ORDER BY id ASC")
    fun getAllCharacters(): List<CharactersInfo>
}