package com.baymax.quotable.authors_fragment.data


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Authors")
data class Author(
    val bio: String,
    @PrimaryKey(autoGenerate = false)
    @SerializedName("_id")
    val id: String,
    val link: String,
    val name: String,
    val quoteCount: Int
)