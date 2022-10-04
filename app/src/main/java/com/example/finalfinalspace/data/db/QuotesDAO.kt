package com.example.finalfinalspace.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.finalfinalspace.data.db.models.CharacterWithQuotesInfo
import com.example.finalfinalspace.data.db.models.QuotesInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface QuotesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote: QuotesInfo): Long

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
    @Transaction
    fun getCharactersWithQuotes(): Flow<List<CharacterWithQuotesInfo>>

    @Query(
        """SELECT quotes_db.* 
            FROM quotes_db
            WHERE  (quotes_db.by_who LIKE :filter OR quotes_db.quote LIKE :filter)
            ORDER BY quotes_db.by_who, quotes_db.quote
        """
    )
    @Transaction
    fun getFilteredQuotes(filter: String): PagingSource<Int, QuotesInfo>
}
