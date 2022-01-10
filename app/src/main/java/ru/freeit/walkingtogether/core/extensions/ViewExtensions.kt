package ru.freeit.walkingtogether.core.extensions

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import ru.freeit.walkingtogether.R
import kotlin.math.roundToInt

fun Context.color(@ColorRes res: Int) = ContextCompat.getColor(this, res)

fun View.snackBar(@StringRes textResId: Int, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, textResId, length).show()
}

fun View.snackBar(str: CharSequence, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, str, length).show()
}

fun Int.dp(ctx: Context) = (ctx.resources.displayMetrics.density * this).roundToInt()
fun Int.dpf(ctx: Context) = ctx.resources.displayMetrics.density * this

fun AppCompatRadioButton.runIfChecked(onChecked: () -> Unit) {
    if (isChecked) onChecked()
}

fun EditText.str() = text.toString()

fun View.visible() {
    isVisible = true
}

fun Button.disable() { this.isEnabled = false }
fun Button.enable() { this.isEnabled = true }

fun View.gone() {
    isVisible = false
}

fun TextInputLayout.error(@StringRes textResId: Int) {
    error = resources.getString(textResId)
}

fun TextInputLayout.resetError() {
    error = null
}

fun View.click(listener: () -> Unit) {
    setOnClickListener { listener() }
}

fun View.animateIncreasingHeight(toHeight: Int, delay: Long = 200L, onEnd: (animator: Animator) -> Unit = {}) {
    ValueAnimator.ofInt(0, toHeight).apply {
        addUpdateListener { animatedValue ->
            val params = layoutParams
            params.height = animatedValue.animatedValue as Int
            layoutParams = params
        }
        doOnEnd(onEnd)
        duration = delay
        start()
    }
}

fun View.animateDecreasingHeight(delay: Long = 200L, onEnd: (animator: Animator) -> Unit = {}) {
    ValueAnimator.ofInt(measuredHeight, 0).apply {
        addUpdateListener { animatedValue ->
            val params = layoutParams
            params.height = animatedValue.animatedValue as Int
            layoutParams = params
        }
        doOnEnd(onEnd)
        duration = delay
        start()
    }
}

fun ImageView.setImageResourceWithAnimating(@DrawableRes res: Int) {
    animate().alpha(0f)
        .withEndAction {
            setImageResource(res)
            animate().alpha(1f).setDuration(150L).start()
        }
        .setDuration(150L).start()
}