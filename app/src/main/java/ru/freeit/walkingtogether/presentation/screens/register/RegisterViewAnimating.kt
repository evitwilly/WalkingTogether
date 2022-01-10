package ru.freeit.walkingtogether.presentation.screens.register

import android.util.Log
import android.view.View
import ru.freeit.walkingtogether.core.extensions.animateDecreasingHeight
import ru.freeit.walkingtogether.core.extensions.animateIncreasingHeight
import ru.freeit.walkingtogether.core.extensions.dpf

class RegisterViewAnimating(private val view1: View, private val view2: View, private val view3: View) {

    private var savedHeight1 = 0
    private var savedHeight2 = 0
    private val translationDuration = 200L

    fun animate(isVisible: Boolean) {
        if (isVisible) {

            if (view1.measuredHeight != 0) {
                savedHeight1 = view1.measuredHeight
            }

            if (view2.measuredHeight != 0) {
                savedHeight2 = view2.measuredHeight
            }

            view1.animate()
                .withEndAction(view1::animateDecreasingHeight)
                .translationX((-500).dpf(view1.context))
                .setDuration(translationDuration).start()

            view2.animate()
                .withEndAction(view2::animateDecreasingHeight)
                .translationX(500.dpf(view2.context))
                .setDuration(translationDuration).start()

            view3.animate()
                .translationY((-100).dpf(view3.context))
                .setDuration(translationDuration)
                .start()

        } else {
            view1.animateIncreasingHeight(savedHeight1) {
                view1.animate().translationX(0f).setDuration(translationDuration).start()
            }
            view2.animateIncreasingHeight(savedHeight2) {
                view2.animate().translationX(0f).setDuration(translationDuration).start()
            }

            view3.animate().translationY(0f).setDuration(translationDuration).start()
        }
    }

}