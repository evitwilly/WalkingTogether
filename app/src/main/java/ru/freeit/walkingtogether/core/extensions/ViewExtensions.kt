package ru.freeit.walkingtogether.core.extensions

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import ru.freeit.walkingtogether.R

fun Context.color(@ColorRes res: Int) = ContextCompat.getColor(this, res)

fun View.snackBar(@StringRes textResId: Int, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, textResId, length).show()
}

fun View.snackBar(str: CharSequence, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, str, length).show()
}

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