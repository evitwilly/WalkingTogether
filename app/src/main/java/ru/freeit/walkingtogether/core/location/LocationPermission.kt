package ru.freeit.walkingtogether.core.location

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class LocationPermission {
    private var launcher: ActivityResultLauncher<String>? = null

    private val granted = PackageManager.PERMISSION_GRANTED
    private val locationPm = ACCESS_FINE_LOCATION

    fun listen(fragment: Fragment, isAllowed: () -> Unit = {}, isDenied: () -> Unit = {}) {
        launcher = fragment.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                isAllowed()
            } else {
                isDenied()
            }
        }
    }

    fun isGranted(ctx: Context) = granted == ContextCompat.checkSelfPermission(ctx, locationPm)

    fun requestPermission(ctx: Context, isAllowed: () -> Unit) {
        val permissionGranted = ContextCompat.checkSelfPermission(ctx, locationPm)
        if (permissionGranted == granted) {
            isAllowed()
        } else {
            launcher?.launch(locationPm)
        }
    }

}