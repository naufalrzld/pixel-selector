package com.telkom.library

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import com.telkom.library.library.R
import kotlin.math.roundToInt

class PixelImageView : AppCompatImageView {

    constructor(context: Context) : super(context) {
        init(context, null)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context, attrs)
    }

    private val pixels = mutableListOf<MutableList<PixelSelectedData>>()

    private val _matrix = mutableListOf<MutableList<Int>>()
    val matrix: List<List<Int>> = _matrix

    var mode: Mode = Mode.None

    private var strokeColor: Int = R.color.white
    private var selectedColor: Int = R.color.color_selected

    @SuppressLint("Recycle")
    private fun init(context: Context, attrs: AttributeSet?) {
        var typedArray: TypedArray? = null
        if (attrs != null) {
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.PixelImageView)
        }
        strokeColor = optColor(
            typedArray,
            R.styleable.PixelImageView_piv_stroke_color,
            Color.WHITE
        )
        selectedColor = optColor(
            typedArray,
            R.styleable.PixelImageView_piv_selected_color,
            R.color.color_selected
        )
    }

    @SuppressLint("DrawAllocation", "ResourceAsColor")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val density = resources.displayMetrics.density
        val lengthX = ((16 * density * .5f).roundToInt())
        val lengthY = (9 * density * .5f).roundToInt()
        val w = width / lengthX
        val h = height / lengthY
        val myPaint = Paint()
        if (pixels.isEmpty()) {
            myPaint.color = strokeColor
            myPaint.strokeWidth = 1f
            myPaint.style = Paint.Style.STROKE

            for (i in 1..lengthY) {
                val row = mutableListOf<PixelSelectedData>()
                val mtx = mutableListOf<Int>()
                val height = (h * i).toFloat()
                for (j in 1..lengthX) {
                    val width = (w * j).toFloat()
                    row.add(PixelSelectedData(width - w, height - h, width, height))
                    mtx.add(0)
                    canvas.drawRect(width - w, height - h, width, height, myPaint)
                }
                pixels.add(row)
                _matrix.add(mtx)
            }
        } else {
            pixels.forEach {
                it.forEach { data ->
                    if (data.isSelected) {
                        myPaint.color = selectedColor
                        myPaint.style = Paint.Style.FILL
                        canvas.drawRect(data.left, data.top, data.right, data.bottom, myPaint)
                    } else {
                        myPaint.color = strokeColor
                        myPaint.style = Paint.Style.STROKE
                        canvas.drawRect(data.left, data.top, data.right, data.bottom, myPaint)
                    }
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (mode == Mode.None) return false
        if (event.action == MotionEvent.ACTION_MOVE || event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_MOVE) {
            var i = 0
            var iFound = false
            while (i < pixels.size && !iFound) {
                var j = 0
                var jFound = false
                while (j < pixels[i].size && !jFound) {
                    if ((event.x >= pixels[i][j].left && event.x <= pixels[i][j].right) && (event.y >= pixels[i][j].top && event.y <= pixels[i][j].bottom)) {
                        pixels[i][j].isSelected = mode == Mode.Selection
                        _matrix[i][j] = if (mode == Mode.Selection) 1 else 0
                        invalidate()
                        jFound = true
                        iFound = true
                    }
                    j++
                }
                i++
            }
        }
        return true
    }

    fun clearSelector() {
        pixels.clear()
        _matrix.clear()
        invalidate()
    }

    companion object {
        private fun optColor(
            typedArray: TypedArray?,
            index: Int,
            def: Int
        ): Int {
            return typedArray?.getColor(index, def) ?: def
        }
    }

    enum class Mode {
        None, Selection, Erase
    }
}