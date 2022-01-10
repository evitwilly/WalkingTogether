package ru.freeit.walkingtogether.core.viewmodel

import android.content.Context
import android.os.Bundle
import androidx.savedstate.SavedStateRegistryOwner
import ru.freeit.walkingtogether.core.google.GoogleSignClient
import ru.freeit.walkingtogether.data.firebasedb.UserFirebaseDatabase
import ru.freeit.walkingtogether.data.firebasedb.entity.LocalUserRepository
import ru.freeit.walkingtogether.presentation.MainViewModelFactory
import ru.freeit.walkingtogether.presentation.screens.intro.IntroViewModelFactory
import ru.freeit.walkingtogether.presentation.screens.profile.NameViewModelFactory
import ru.freeit.walkingtogether.presentation.screens.profile.ProfileViewModelFactory
import ru.freeit.walkingtogether.presentation.screens.register.AvatarImages
import ru.freeit.walkingtogether.presentation.screens.register.RegisterViewModelFactory

class ViewModelFactories(
    private val database: UserFirebaseDatabase,
    private val userRepo: LocalUserRepository,
    private val images: AvatarImages,
) {
    fun register(id: String, owner: SavedStateRegistryOwner, bundle: Bundle?) = RegisterViewModelFactory(id, database, userRepo, images, owner, bundle)
    fun intro(ctx: Context) = IntroViewModelFactory(GoogleSignClient(ctx), userRepo, database)
    fun main() = MainViewModelFactory(userRepo)
    fun profile() = ProfileViewModelFactory(userRepo, images, database)
    fun name() = NameViewModelFactory(userRepo, database)
}