package com.amk.bazaarinapplogin

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farsitel.bazaar.core.BazaarSignIn
import com.farsitel.bazaar.core.model.BazaarSignInOptions
import com.farsitel.bazaar.core.model.SignInOption
import com.farsitel.bazaar.storage.BazaarStorage
import com.farsitel.bazaar.storage.callback.BazaarStorageCallback
import com.farsitel.bazaar.util.ext.toReadableString
import kotlinx.coroutines.launch

class MainActivityViewModel(

) : ViewModel() {

    val userID = mutableStateOf("")
    val savedData = mutableStateOf("")
    val getSavedData = mutableStateOf("")

    // Bazaar InApp Login
    fun signInBazaar(context: Context, activity: Activity, lifecycleOwner: LifecycleOwner) {
        viewModelScope.launch {

            val signInOption = BazaarSignInOptions.Builder(SignInOption.DEFAULT_SIGN_IN).build()
            val client = BazaarSignIn.getClient(context, signInOption)

            val intent = client.getSignInIntent()

            ActivityCompat.startActivityForResult(activity, intent, 1, null)

            BazaarSignIn.getLastSignedInAccount(
                context, lifecycleOwner
            ) { response ->

                val account = response?.data

                userID.value = account?.accountId ?: "User Not Found"

            }
        }

    }

    // save data to Bazaar
    fun saveData(context: Context, lifecycleOwner: LifecycleOwner) {
        BazaarStorage.saveData(context,
            lifecycleOwner,
            data = "AmirMohammad Kordlu".toByteArray(),
            BazaarStorageCallback { savedResponse ->
                savedData.value = savedResponse?.data?.toReadableString().toString()
            })
    }

    // read saved data
    fun getSavedData(context: Context, lifecycleOwner: LifecycleOwner) {
        BazaarStorage.getSavedData(
            context,
            lifecycleOwner,
            callback = BazaarStorageCallback { response ->
                getSavedData.value = response?.data?.toReadableString().toString()
            }

        )
    }

}