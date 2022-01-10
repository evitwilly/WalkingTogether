package ru.freeit.walkingtogether.presentation.base

import ru.freeit.walkingtogether.R

interface AvatarImages {

    fun drawableBy(id: Int) : AvatarImage

    fun randomFemale() : AvatarImage
    fun randomMale() : AvatarImage

    fun female() : List<AvatarImage>
    fun male() : List<AvatarImage>

    fun isMale(img: AvatarImage?) : Boolean
    fun isFemale(img: AvatarImage?) : Boolean

    class Base : AvatarImages {

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

        override fun drawableBy(id: Int) = images[id]

        override fun randomFemale() = female.random()
        override fun randomMale() = male.random()

        override fun female() = female
        override fun male() = male

        override fun isFemale(img: AvatarImage?): Boolean {
            if (img == null) {
                return false
            }
            return female.contains(img)
        }

        override fun isMale(img: AvatarImage?): Boolean {
            if (img == null) {
                return false
            }
            return male.contains(img)
        }
    }

}