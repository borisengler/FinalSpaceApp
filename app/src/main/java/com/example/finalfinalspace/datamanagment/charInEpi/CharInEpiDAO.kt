package com.example.finalfinalspace.datamanagment.charInEpi

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface CharInEpiDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(charsInEpis: List<CharInEpiInfo>): List<Long>
}