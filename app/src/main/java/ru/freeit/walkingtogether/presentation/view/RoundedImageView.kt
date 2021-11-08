package ru.freeit.walkingtogether.presentation.view

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class RoundedImageView @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(ctx, attrs, defStyleAttr) {

    // для использования независимых от разных расширений экрана пикселей
    // мы должны использовать специальное Kotlin расширение
    private val Int.dp
        get() = context.resources.displayMetrics.density * this

    private val roundedBitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xff424242.toInt()
    }

    // метод onDraw отрисовываем наше изображение закругленным
    override fun onDraw(canvas: Canvas) {
        val drawable = drawable ?: return
        if (width == 0 || height == 0) {
            return
        }
        val bitmap = (drawable as BitmapDrawable).bitmap.centerCrop()
        val roundedBitmap = rounded(bitmap, 100.dp)
        canvas.drawBitmap(roundedBitmap, 0f, 0f, null)
    }

    private fun Bitmap.centerCrop() : Bitmap {

        return if (width >= height){

            Bitmap.createBitmap(
                this,
                width /2 - height /2,
                0,
                height,
                height
            );

        } else {

            Bitmap.createBitmap(
                this,
                0,
                height /2 - width /2,
                width, width
            );
        }
    }

    // простыми словами, метод rounded принимает обычное изображение,
    // а возвращает закруглённое
    private fun rounded(bitmap: Bitmap, radius: Float) : Bitmap {
        val result = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(result)
        canvas.drawARGB(0, 0, 0, 0)

        val rectF = RectF(0f, 0f, bitmap.width / 1f, bitmap.height / 1f)
        canvas.drawRoundRect(rectF,  radius, radius, roundedBitmapPaint)
        roundedBitmapPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        canvas.drawBitmap(bitmap, rect, rect, roundedBitmapPaint)

        return result
    }


}