package com.zerodevbuilds.eated

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.zerodevbuilds.eated.ui.navigation.EatedNavigation
import com.zerodevbuilds.eated.ui.theme.EatedTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val app = application as EatedApplication
        setContent {
            EatedTheme {
                EatedNavigation(repository = app.repository)
            }
        }
    }
}
