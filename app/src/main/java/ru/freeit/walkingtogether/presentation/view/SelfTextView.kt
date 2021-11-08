package ru.freeit.walkingtogether.presentation.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.text.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.withStyledAttributes
import ru.freeit.walkingtogether.R

class SelfTextView @kotlin.jvm.JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(ctx, attrs, defStyleAttr) {
    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 40f.sp
    }

    private val Float.sp
        get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this, resources.displayMetrics)

    private var textLayout: Layout? = null

    private var editable: Editable = SpannableStringBuilder()

    init {
        context.withStyledAttributes(attrs, R.styleable.SelfTextView) {
            val startString = getString(R.styleable.SelfTextView_startString) ?: ""
            val endString = getString(R.styleable.SelfTextView_endString) ?: ""
            ValueAnimator.ofObject(StringEvaluator(), startString, endString).apply {
                duration = 2000L

                addUpdateListener { animator ->
                    val animatedValue = animator.animatedValue.toString()

                    val prevLineCount = textLayout?.lineCount
                    editable.replace(0, editable.length, animatedValue)
                    if (textLayout?.lineCount != prevLineCount) {
                        requestLayout()
                    }

                    invalidate()
                }
                start()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val width = if (widthSpecSize > 0) widthSpecSize else 500

        val height = textLayout?.height ?: (textPaint.descent() - textPaint.ascent()).toInt()

        setMeasuredDimension(width, height)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        if (w == oldw) return

        textLayout = DynamicLayout(editable, textPaint, w, Layout.Alignment.ALIGN_CENTER, 1f, 0f, false)
    }

    override fun onDraw(canvas: Canvas?) { textLayout?.draw(canvas) }
}