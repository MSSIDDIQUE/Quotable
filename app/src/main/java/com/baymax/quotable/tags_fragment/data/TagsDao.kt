package com.baymax.quotable.tags_fragment.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TagsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTags(tags : List<Tag>)

    @Query("Select * from Tags")
    fun getAllTags():LiveData<List<Tag>>

    @Query("DELETE from Tags")
    fun deleteAllTags()

}