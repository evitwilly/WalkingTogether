package ru.freeit.walkingtogether.presentation.base

import android.graphics.Rect
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import ru.freeit.walkingtogether.core.App
import android.util.TypedValue

import android.os.Build

import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.appcompat.app.AppCompatActivity


abstract class BaseFragment(@LayoutRes layoutRes: Int) : Fragment(layoutRes) {

    protected val ctx by lazy {
        requireContext()
    }

    protected val factories by lazy {
        (requireActivity().application as App).viewModelFactories
    }

    protected val activity by lazy {
        requireActivity() as AppCompatActivity
    }

    protected val navigator by lazy {
        MyNavigator(requireActivity().supportFragmentManager)
    }

    protected val fm by lazy { requireActivity().supportFragmentManager }

    protected fun onKeyboardVisibility(listener: (isVisible: Boolean) -> Unit) {
        val parentView = requireView()
        parentView.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            private var alreadyOpen = false
            private val defaultKeyboardHeightDP = 100
            private val EstimatedKeyboardDP = defaultKeyboardHeightDP + if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) 48 else 0
            private val rect: Rect = Rect()

            override fun onGlobalLayout() {
                val estimatedKeyboardHeight = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    EstimatedKeyboardDP.toFloat(),
                    parentView.resources.displayMetrics
                ).toInt()

                parentView.getWindowVisibleDisplayFrame(rect)

                val heightDiff: Int = parentView.rootView.height - (rect.bottom - rect.top)
                val isShown = heightDiff >= estimatedKeyboardHeight
                if (isShown == alreadyOpen) { return }

                alreadyOpen = isShown
                listener(isShown)
            }
        })
    }
}