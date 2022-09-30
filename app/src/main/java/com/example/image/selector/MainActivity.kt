package com.example.image.selector

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.image.selector.databinding.ActivityMainBinding
import com.telkom.library.PixelImageView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (requestedOrientation != -1) {
            val attr = window.attributes
            if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT) {
                supportActionBar?.show()
                attr.flags = attr.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN.inv()
            } else {
                supportActionBar?.hide()
                attr.flags = attr.flags or WindowManager.LayoutParams.FLAG_FULLSCREEN
            }
            window.attributes = attr
        }

        Glide.with(this).load(
            ContextCompat.getDrawable(this, R.drawable.sample)
        ).into(binding.ivImage)

        binding.tvMode.text = "Mode: ${binding.ivImage.mode}"

        binding.btnSelect.setOnClickListener {
            val mode = if (binding.ivImage.mode == PixelImageView.Mode.None ||
                binding.ivImage.mode == PixelImageView.Mode.Erase
            ) PixelImageView.Mode.Selection else PixelImageView.Mode.None
            binding.ivImage.mode = mode
            binding.tvMode.text = "Mode: ${binding.ivImage.mode}"
        }

        binding.btnErase.setOnClickListener {
            val mode = if (binding.ivImage.mode == PixelImageView.Mode.None ||
                binding.ivImage.mode == PixelImageView.Mode.Selection
            ) PixelImageView.Mode.Erase else PixelImageView.Mode.None
            binding.ivImage.mode = mode
            binding.tvMode.text = "Mode: ${binding.ivImage.mode}"
        }

        binding.btnClear.setOnClickListener {
            binding.ivImage.clearSelector()
        }

        binding.btnGetMatrix.setOnClickListener {
            val matrix = binding.ivImage.matrix
            Log.d("MATRIX", "$matrix")
        }
    }
}