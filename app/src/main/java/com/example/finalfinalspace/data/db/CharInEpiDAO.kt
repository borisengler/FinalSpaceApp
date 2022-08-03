package com.example.finalfinalspace.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.finalfinalspace.data.db.models.CharInEpiInfo

@Dao
interface CharInEpiDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(charsInEpis: List<CharInEpiInfo>): List<Long>
}
