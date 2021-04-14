package com.baymax.quotable.ui.fragments.quotes_fragment.data

import androidx.room.*

@Dao
interface QuotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllQuotes(quotes : List<Quote>)

    @Query("Select * from Quotes")
    suspend fun getAllQuotes():List<Quote>

    @Query("DELETE from Quotes")
    suspend fun deleteAllQuotes()

}