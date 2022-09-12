package com.example.musicplayer.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity

@Entity(tableName = "tb_favourite", primaryKeys = ["idUser", "idSong"])
data class Favourite(
    val idUser: Int,
    val idSong: Int
)

