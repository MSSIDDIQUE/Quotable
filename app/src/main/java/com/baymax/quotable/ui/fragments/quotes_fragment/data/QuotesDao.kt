package com.baymax.quotable.ui.fragments.quotes_fragment.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface QuotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllQuotes(quotes : List<Quote>)

    @Query("Select * from Quotes")
    fun getAllQuotes():LiveData<List<Quote>>

    @Query("DELETE from Quotes")
    fun deleteAllQuotes()

}