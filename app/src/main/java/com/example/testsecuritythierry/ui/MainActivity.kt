package com.example.testsecuritythierry.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.testsecuritythierry.ui.components.MainScreen
import com.example.testsecuritythierry.ui.theme.TestSecurityThierryTheme
import com.example.testsecuritythierry.ui.view_models.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainActivityViewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivityViewModel.init(this)

        setContent {
            TestSecurityThierryTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    MainScreen(activity = this)
                }
            }
        }

        mainActivityViewModel.embedServer(this)
    }
}
