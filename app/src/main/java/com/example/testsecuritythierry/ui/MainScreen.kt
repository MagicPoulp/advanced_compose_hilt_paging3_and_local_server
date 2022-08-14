package com.example.testsecuritythierry.ui

import android.app.Activity
import android.content.pm.PackageManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.testsecuritythierry.MainActivity
import com.example.testsecuritythierry.R
import com.example.testsecuritythierry.viewmodels.ApplicationsInspectorViewModel
import com.example.testsecuritythierry.viewmodels.UiState
import org.koin.androidx.compose.getViewModel

@Composable
fun MainScreen(packageManager: PackageManager,
        applicationsInspectorViewModel: ApplicationsInspectorViewModel = getViewModel(),
        activity: MainActivity
) {
    applicationsInspectorViewModel.init(
        virusTotalRawApiKey = activity.resources.getString( R.string.total_api_key),
        owner = activity,
        packageManager = packageManager
    )
    Column(modifier = Modifier.fillMaxSize()
        .background(MaterialTheme.colors.secondary)) {
        TopBarTitle()
        val state by applicationsInspectorViewModel.uiState.observeAsState()
        when (state) {
            UiState.Filled -> Text(text = "Hello! size: ${applicationsInspectorViewModel.listPackages.value?.size}")
            else -> Row {
                ProgressIndicator()
            }
        }
    }
}

@Composable
fun TopBarTitle() {
    Row(modifier = Modifier.padding(start = 60.dp, end = 60.dp).height(80.dp)
        .background(MaterialTheme.colors.secondary),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically) {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()) {
            Text(text = "ApplicationsInspector is analyzing your apps...",
                style = MaterialTheme.typography.h1,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ProgressIndicator() {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.secondary)
    ) {
        CircularProgressIndicator()
    }
}