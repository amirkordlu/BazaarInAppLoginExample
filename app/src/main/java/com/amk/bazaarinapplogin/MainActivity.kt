package com.amk.bazaarinapplogin

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.amk.bazaarinapplogin.ui.theme.BazaarInAppLoginTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BazaarInAppLoginTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val activity = LocalContext.current as Activity
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val viewModel = MainActivityViewModel()

    BazaarButton {
        // get Bazaar user id and use it
        viewModel.signInBazaar(context, activity, lifecycleOwner)
        Toast.makeText(context, viewModel.userID.value, Toast.LENGTH_SHORT).show()
    }

}

@Composable
fun BazaarButton(signInClicked: () -> Unit) {
    AndroidView(
        modifier = Modifier.clickable { signInClicked.invoke() },
        factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.login_bazaar, null, false)
            view
        },
        update = { view ->
            // Update the view
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BazaarInAppLoginTheme {
        MainScreen()
    }
}