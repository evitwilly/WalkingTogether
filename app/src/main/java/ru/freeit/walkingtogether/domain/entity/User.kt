package ru.freeit.walkingtogether.domain.entity

import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import ru.freeit.walkingtogether.data.firebasedb.entity.FirebaseUser

import ru.freeit.walkingtogether.presentation.screens.register.AvatarImage

data class User(
    private val id: String,
    private val name: String = "",
    private val bio: String = "",
    private val isFemale: Boolean = true,
    private val avatar: AvatarImage
) {
    fun isFemale() = isFemale

    fun img(image: ImageView) {
        avatar.img(image)
    }
    fun name(text: TextView) {
        text.text = name
    }

    fun bioText(view: TextView) {
        view.text = "Lorem ipsum amet dolor asdff salkkd woo lslsdkfkdl ddfkf llfdkl."
    }

    fun bioEdit(text: AppCompatEditText) {
        text.setText(bio)
    }
    fun checkFemale(radio: RadioButton) {
        radio.isSelected = isFemale
    }
    fun toFirebase() = FirebaseUser(id, name, bio, isFemale, avatar.id())
}