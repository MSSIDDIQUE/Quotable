package com.baymax.quotable.ui.fragments.authors_fragment.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AuthorsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAuthors(authors : List<Author>)

    @Query("Select * from Authors")
    suspend fun getAllAuthors():List<Author>

    @Query("DELETE from Authors")
    suspend fun deleteAllAuthors()

}