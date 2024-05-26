package com.stslex.atten

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.stslex.atten.core.common.isDebug
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.logger.Level

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            AndroidApp {
                androidLogger(
                    level = if (isDebug) {
                        Level.DEBUG
                    } else {
                        Level.NONE
                    }
                )
                androidContext(this@MainActivity)
            }
        }
    }
}

@Composable
private fun AndroidApp(
    additionalSetup: KoinApplication.() -> Unit = {},
) {
    App(additionalSetup = additionalSetup)
}

@Preview
@Composable
private fun AndroidAppPreview() {
    AndroidApp()
}
