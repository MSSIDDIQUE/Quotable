package com.baymax.quotable.ui.fragments.tags_fragment.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TagsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTags(tags : List<Tag>)

    @Query("Select * from Tags")
    suspend fun getAllTags():List<Tag>

    @Query("DELETE from Tags")
    suspend fun deleteAllTags()

}