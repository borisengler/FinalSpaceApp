package com.example.finalfinalspace.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.finalfinalspace.data.db.models.CharacterWithQuotesInfo
import com.example.finalfinalspace.data.db.models.QuotesInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface QuotesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articleList: List<QuotesInfo>): List<Long>

    @Query("SELECT * from quotes_db ORDER BY by_who, quote ASC")
    fun getAllQuotes(): Flow<List<QuotesInfo>>

    @Query(
        """SELECT DISTINCT characters.*
        FROM characters JOIN quotes_db
        ON characters.name = quotes_db.by_who
        ORDER BY name
        """
    )
    fun getCharactersWithQuotes(): Flow<List<CharacterWithQuotesInfo>>
}