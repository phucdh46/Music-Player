package com.example.musicplayer.test;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.example.musicplayer.databinding.ActivityTestDatabaseBinding;
import com.example.musicplayer.db.MusicDatabase;
import com.example.musicplayer.model.Favourite;
import com.example.musicplayer.model.Playlist;
import com.example.musicplayer.model.Song;
import com.example.musicplayer.model.User;
import com.example.musicplayer.model.relation.FavouriteSongCrossRef;
import com.example.musicplayer.model.relation.SongPlaylistCrossRef;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/example/musicplayer/test/TestDatabaseActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "TAG", "", "binding", "Lcom/example/musicplayer/databinding/ActivityTestDatabaseBinding;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"})
public final class TestDatabaseActivity extends androidx.appcompat.app.AppCompatActivity {
    private final java.lang.String TAG = "DHP";
    private com.example.musicplayer.databinding.ActivityTestDatabaseBinding binding;
    
    public TestDatabaseActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
}