package ru.freeit.walkingtogether.core.extensions

import android.content.Context
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
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