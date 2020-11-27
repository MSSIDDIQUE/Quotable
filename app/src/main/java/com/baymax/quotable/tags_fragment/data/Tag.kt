package com.baymax.quotable.tags_fragment.data


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Tags")
data class Tag(
    @SerializedName("_id")
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val quoteCount: Int,
    @SerializedName("__v")
    val v: Int
)