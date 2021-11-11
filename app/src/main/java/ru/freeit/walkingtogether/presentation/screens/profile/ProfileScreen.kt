package ru.freeit.walkingtogether.presentation.screens.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.freeit.walkingtogether.core.App
import ru.freeit.walkingtogether.data.firebasedb.entity.FirebaseUser

import ru.freeit.walkingtogether.databinding.ProfileScreenBinding
import ru.freeit.walkingtogether.presentation.screens.register.AvatarImages

class ProfileScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = ProfileScreenBinding.inflate(inflater, container, false)

        val appPrefs = (requireActivity().application as App).appPrefs
        val user = FirebaseUser.restore(appPrefs).toDomain(AvatarImages()).apply {
            img(binding.avatarImage)
            name(binding.nameEdit)
            bio(binding.bioEdit)
            checkFemale(binding.femaleCheckbox)
        }

        return binding.root
    }

}