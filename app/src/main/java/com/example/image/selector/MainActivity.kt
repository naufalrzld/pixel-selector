package com.example.image.selector

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.image.selector.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var eraserMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this).load(
            ContextCompat.getDrawable(this, R.drawable.sample)
        ).into(binding.ivImage)

        binding.btnClear.setOnClickListener {
            binding.ivImage.clearSelector()
        }

        binding.btnErase.setOnClickListener {
            eraserMode = !eraserMode
            binding.ivImage.eraserMode(eraserMode)
        }

        binding.btnGetMatrix.setOnClickListener {
            val matrix = binding.ivImage.matrix
            Log.d("NOPAL", "$matrix")
        }
    }
}