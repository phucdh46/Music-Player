package com.example.musicplayer.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.musicplayer.model.Favourite
import com.example.musicplayer.model.Playlist
import com.example.musicplayer.model.Song
import com.example.musicplayer.model.User
import com.example.musicplayer.model.relation.*
import kotlinx.coroutines.flow.Flow


@Dao
interface MusicDao {

    //INSERT Thêm dữ liệu từ API vào Local DB
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSong(song: Song)

    //SELECT song by id
    @Query("SELECT * FROM tb_song WHERE idSong=:id")
    fun getSongById(id: Int): LiveData<Song>

    //SELECT Song rank
    @Query("SELECT * FROM tb_song WHERE isOffline=:isOffline")
    fun getSongsOnline(isOffline: Int): LiveData<MutableList<Song>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: Playlist)

    @Query("SELECT * FROM tb_user WHERE idUser=:id")
    fun getNameById(id: Int): LiveData<User>

    @Query("SELECT * FROM tb_user")
    suspend fun getName(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(favourite: Favourite)

    //insert song_playlist
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSongPlaylistCrossRef(songPlaylistCrossRef: SongPlaylistCrossRef)

    //insert song_favourite
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteSongCrossRef(favouriteSongCrossRef: FavouriteSongCrossRef)

    //UPDATE
    @Update
    suspend fun updateSong(song: Song)

    @Update
    suspend fun updateUser(user: User)

    @Query("UPDATE tb_song SET urlSong = :urlSong AND isOffline=:status WHERE idSong =:id")

    suspend fun updateUrlSong(urlSong: String, status: Boolean, id: Int)

    @Query("UPDATE tb_playlist SET name = :name WHERE idPlaylist =:id")
    suspend fun updatePlaylist(name: String, id: Int)


    //DELETE
    @Query("DELETE FROM tb_song WHERE idSong=:id")
    suspend fun deleteSong(id: Int)

    @Query("SELECT * FROM tb_song WHERE isOffline =:status AND nameSong LIKE '%' || :searchQuery || '%' ORDER BY nameSong ASC")
    fun getSongs(searchQuery: String, status: Boolean): Flow<List<Song>>

    @Query("SELECT * FROM tb_playlist INNER JOIN tb_user ON tb_playlist.idUserCreator=tb_user.idUser WHERE tb_user.idUser =:idUser AND tb_playlist.name LIKE '%' || :searchQuery || '%' ORDER BY tb_playlist.name ASC")
    fun getPlaylists(searchQuery: String, idUser: Int): Flow<List<Playlist>>

    @Query("SELECT * FROM tb_song WHERE nameSong LIKE '%' || :searchQuery || '%'")
    fun getFlowAllSongs(searchQuery: String): Flow<List<Song>>

    @Query("DELETE FROM tb_playlist WHERE idPlaylist=:id")
    suspend fun deletePlaylist(id: Int)

    @Query("DELETE FROM songplaylistcrossref WHERE idPlaylist=:id")
    suspend fun deletePlaylistSongs(id: Int)

    @Query("DELETE FROM tb_favourite WHERE idUser=:idUser AND idSong=:idSong")
    suspend fun deleteFavouriteSong(idUser: Int, idSong: Int)

    @Query("DELETE FROM SongPlaylistCrossRef WHERE idPlaylist=:idPlaylist AND idSong=:idSong")
    suspend fun deleteSongOfPlaylist(idPlaylist: Int, idSong: Int)


    //QUERY
    @Query("SELECT * FROM tb_user WHERE email=:email")
    suspend fun getUser(email: String): User

    @Query("SELECT * FROM tb_song")
    fun getAllSongs(): LiveData<List<Song>>

    @Query("SELECT * FROM tb_song")
    suspend fun getSongs(): List<Song>

    //get all local song with isOffline = true
    @Query("SELECT * FROM tb_song WHERE isOffline=:status ")
    suspend fun getLocalSongs(status: Boolean): List<Song>

    //get song by name
    @Query("SELECT * FROM tb_song WHERE nameSong=:name ")
    suspend fun getSongByName(name: String): Song

    @Query("SELECT * FROM tb_favourite WHERE idUser=:idUser AND idSong=:idSong")
    suspend fun getFavouriteSong(idUser: Int, idSong: Int): Favourite

    //get playlist of song
    @Query("SELECT tb_playlist.* FROM tb_playlist INNER JOIN tb_user ON tb_playlist.idUserCreator=tb_user.idUser INNER JOIN songplaylistcrossref ON tb_playlist.idPlaylist=songplaylistcrossref.idPlaylist WHERE tb_playlist.idUserCreator=:idUser AND songplaylistcrossref.idSong=:idSong")
    suspend fun getPlaylistOfSong(idUser: Int, idSong: Int): List<Playlist>

    //get list song of playlist
    @Transaction
    @Query("SELECT * FROM tb_playlist WHERE idPlaylist=:id")
    suspend fun getSongsOfPlaylist(id: Int): List<PlaylistWithSongs>

    //get user playlist song
    @Transaction
    @Query("SELECT * FROM tb_user WHERE idUser=:id")
    suspend fun getUserWithPlaylistsAndSongs(id: Int): UserWithPlaylistsAndSongs

    //get list song of favourite
    @Transaction
    @Query("SELECT * FROM tb_user WHERE idUser=:id")
    suspend fun getSongsOfFavourite(id: Int): List<FavouriteWithSongs>


    //get user  favourite song
    /*@Transaction
    @Query("SELECT * FROM tb_user WHERE idUser=:id")
    suspend fun getUserWithFavouriteAndSongs(id: Int): UserWithFavouriteAndSongs*/
}