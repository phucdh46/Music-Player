package com.example.musicplayer.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "tb_song")
data class Song(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val idSong: Int?,
    val nameSong: String?,
    val urlImage: String?,
    var urlSong: String,
    @SerializedName("category")
    val genre: String?,
    val musician: String?,
    val singer: String?,
    val album: String?,
    val release: Int?,
    val duration: String?,
    val views: Int,
    val isOffline: Boolean
) : Serializable