package com.kampalahomes.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kampalahomes.app.ui.KampalaHomesApp
import com.kampalahomes.app.ui.theme.KampalaHomesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KampalaHomesTheme {
                KampalaHomesApp()
            }
        }
    }
}
