package ru.freeit.walkingtogether.presentation.screens.register

import android.widget.ImageView
import ru.freeit.walkingtogether.R

data class AvatarImage(
    private val id: Int,
    private val drawableId: Int,
) {
    fun id() = id
    fun img(view: ImageView) {
        view.setImageResource(drawableId)
    }
}


class AvatarImages {

    private val female = listOf(
        AvatarImage(0, R.drawable.girl1),
        AvatarImage(1, R.drawable.girl2),
        AvatarImage(2, R.drawable.girl3),
        AvatarImage(3, R.drawable.girl4),
        AvatarImage(4, R.drawable.girl5),
        AvatarImage(5, R.drawable.girl6),
        AvatarImage(6, R.drawable.girl7)
    )

    private val male = listOf(
        AvatarImage(7, R.drawable.boy1),
        AvatarImage(8, R.drawable.boy2),
        AvatarImage(9, R.drawable.boy3),
        AvatarImage(10, R.drawable.boy4),
        AvatarImage(11, R.drawable.boy5),
        AvatarImage(12, R.drawable.boy6),
        AvatarImage(13, R.drawable.boy7)
    )

    private val images = female + male

    fun drawableBy(id: Int) = images[id]

    fun randomFemale() = female.random()
    fun randomMale() = male.random()

    fun female() = female
    fun male() = male

    fun isFemale(img: AvatarImage?) : Boolean {
        if (img == null) {
            return false
        }
        return female.contains(img)
    }

    fun isMale(img: AvatarImage?) : Boolean {
        if (img == null) {
            return false
        }
        return male.contains(img)
    }

}