package com.telkom.library

data class PixelSelectedData(
    val left: Float = 0f,
    val top: Float = 0f,
    val right: Float = 0f,
    val bottom: Float = 0f,
    var isSelected: Boolean = false
)
