package ru.freeit.walkingtogether.core.extensions

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.widget.TextView
import ru.freeit.walkingtogether.presentation.screens.intro.helpers.LogoText

fun SpannableString.textColorExclusive(color: Int, start: Int, end: Int) {
    setSpan(ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
}

fun SpannableString.fontSizeExclusive(em: Float, start: Int, end: Int) {
    setSpan(RelativeSizeSpan(em), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
}

fun TextView.logoText() {
    text = LogoText(context).spannable()
}