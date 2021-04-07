package com.baymax.quotable.ui.fragments.quotes_fragment.data
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Quotes")
data class Quote(
    val author: String,
    val content: String,
    @SerializedName("_id")
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val length: Int,
    //val tags: List<String>
)