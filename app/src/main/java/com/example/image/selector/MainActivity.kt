package com.example.image.selector

import android.os.Bundle
import android.util.Log
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

        Glide.with(this).load(
            ContextCompat.getDrawable(this, R.drawable.sample)
        ).into(binding.pivImage)

        binding.tvMode.text = "Mode: ${binding.pivImage.mode}"

        binding.btnSelect.setOnClickListener {
            val mode = if (binding.pivImage.mode == PixelImageView.Mode.None ||
                binding.pivImage.mode == PixelImageView.Mode.Erase
            ) PixelImageView.Mode.Selection else PixelImageView.Mode.None
            binding.pivImage.mode = mode
            binding.tvMode.text = "Mode: ${binding.pivImage.mode}"
        }

        binding.btnErase.setOnClickListener {
            val mode = if (binding.pivImage.mode == PixelImageView.Mode.None ||
                binding.pivImage.mode == PixelImageView.Mode.Selection
            ) PixelImageView.Mode.Erase else PixelImageView.Mode.None
            binding.pivImage.mode = mode
            binding.tvMode.text = "Mode: ${binding.pivImage.mode}"
        }

        binding.btnClear.setOnClickListener {
            binding.pivImage.clearSelector()
        }

        binding.btnGetMatrix.setOnClickListener {
            val matrix = binding.pivImage.matrix
            Log.d("MATRIX", "$matrix")
        }
    }
}