package com.example.musicplayer.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Window
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.musicplayer.R
import com.example.musicplayer.databinding.ActivitySplashScreenBinding
import com.example.musicplayer.model.Song
import com.example.musicplayer.network.SongClient
import com.example.musicplayer.utils.Status
import com.example.musicplayer.vm.SongViewModel
import com.example.musicplayer.vm.SongViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    companion object {
        private const val LOG = "TCR"
    }

    private lateinit var binding: ActivitySplashScreenBinding
    private val viewModel: SongViewModel by viewModels {
        SongViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //ẩn title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //ẩn action bar
        supportActionBar?.hide()
        Handler(Looper.getMainLooper()).postDelayed({
            //Handle API


            //Start Activity
            startActivity(Intent(this, MainActivity::class.java))

            //Tắt luôn splash
            finish()
        }, 100L)
    }
}