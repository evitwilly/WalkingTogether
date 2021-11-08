package ru.freeit.walkingtogether.presentation.screens.auth

import ru.freeit.walkingtogether.R

class AvatarImages {

    private val maleImages = listOf(
        R.drawable.boy1,
        R.drawable.boy2,
        R.drawable.boy3,
        R.drawable.boy4,
        R.drawable.boy5,
        R.drawable.boy6,
        R.drawable.boy7
    )

    private val femaleImages = listOf(
        R.drawable.girl1,
        R.drawable.girl2,
        R.drawable.girl3,
        R.drawable.girl4,
        R.drawable.girl5,
        R.drawable.girl6,
        R.drawable.girl7
    )

    fun randomFemale() = femaleImages.random()
    fun randomMale() = maleImages.random()

    fun maleImages() = maleImages
    fun femaleImages() = femaleImages

}