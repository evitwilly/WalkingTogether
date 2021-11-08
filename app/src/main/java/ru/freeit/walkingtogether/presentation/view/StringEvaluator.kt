package ru.freeit.walkingtogether.presentation.view

import android.animation.TypeEvaluator
import kotlin.math.roundToInt

class StringEvaluator : TypeEvaluator<String> {
    override fun evaluate(fraction: Float, start: String, end: String): String {
        val coercedFraction = fraction.coerceIn(0f, 1f)
        val lengthDiff = end.length - start.length
        val currentDiff = (lengthDiff * coercedFraction).roundToInt()
        return if (currentDiff > 0) {
            end.substring(0, start.length + currentDiff)
        } else {
            start.substring(0, start.length + currentDiff)
        }
    }
}