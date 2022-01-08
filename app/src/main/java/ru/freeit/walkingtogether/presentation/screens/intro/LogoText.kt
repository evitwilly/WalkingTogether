package ru.freeit.walkingtogether.presentation.screens.intro

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import androidx.core.content.ContextCompat
import ru.freeit.walkingtogether.R
import ru.freeit.walkingtogether.core.extensions.color
import ru.freeit.walkingtogether.core.extensions.fontSizeExclusive
import ru.freeit.walkingtogether.core.extensions.textColorExclusive

class LogoText(private val ctx: Context) {
    fun spannable() = SpannableString(ctx.getString(R.string.app_name)).apply {
        textColorExclusive(ctx.color(R.color.purple_300), 0, 4)
        textColorExclusive(ctx.color(R.color.purple_800), 4, 9)
        fontSizeExclusive(1.5f, 0, 4)
        fontSizeExclusive(1.2f, 5, 7)
    }
}