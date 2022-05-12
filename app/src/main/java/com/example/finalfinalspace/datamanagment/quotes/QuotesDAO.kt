package com.example.finalfinalspace.datamanagment.quotes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuotesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: QuotesInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articleList : List<QuotesInfo>) : List<Long>

    @Query("SELECT * from quotes_db WHERE id = :id")
    fun getQuote(id: Int): QuotesInfo

    @Query("SELECT * from quotes_db ORDER BY by_who, quote ASC")
    fun getAllQuotes(): List<QuotesInfo>
}