package ru.freeit.walkingtogether.core.extensions

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import ru.freeit.walkingtogether.R

fun Context.color(@ColorRes res: Int) = ContextCompat.getColor(this, res)

