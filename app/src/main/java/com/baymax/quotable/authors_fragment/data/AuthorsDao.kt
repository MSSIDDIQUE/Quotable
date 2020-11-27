package com.baymax.quotable.authors_fragment.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AuthorsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAuthors(authors : List<Author>)

    @Query("Select * from Authors")
    fun getAllAuthors():LiveData<List<Author>>

    @Query("DELETE from Authors")
    fun deleteAllAuthors()

}