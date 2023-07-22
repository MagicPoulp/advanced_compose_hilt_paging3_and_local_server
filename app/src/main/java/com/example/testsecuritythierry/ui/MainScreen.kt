package com.example.testsecuritythierry.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testsecuritythierry.MainActivity
import com.example.testsecuritythierry.viewmodels.NewsViewModel
import com.example.testsecuritythierry.viewmodels.UiState

@Composable
fun MainScreen(newsViewModel: NewsViewModel = hiltViewModel(),
               activity: MainActivity
) {
    //val numUnfinished by newsViewModel.numUnfinished.observeAsState()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val topAndBottomBarHeight = 80.dp
    newsViewModel.init(
        owner = activity,
    )
    Box(contentAlignment = Alignment.TopCenter,
        modifier = Modifier
        .fillMaxSize() ) {
        //.background(MaterialTheme.colors.secondary)) {
        /*
        // if we want a top bar
        Row(modifier = Modifier
            .height(topAndBottomBarHeight)
            .fillMaxWidth()
            .background(MaterialTheme.colors.secondary)
            .align(alignment = Alignment.TopCenter)
        ) {
            TopBarTitle(numUnfinished)
        }
         */
        Row(modifier = Modifier
            .height(screenHeight - topAndBottomBarHeight * 2)
            .background(MaterialTheme.colors.primaryVariant)
            .fillMaxWidth()
            .align(alignment = Alignment.Center)
        ) {
            val state by newsViewModel.uiState.observeAsState()
            when (state) {
                UiState.Filled -> TableWithAllNews(newsViewModel)
                else -> Row {
                    ProgressIndicator()
                }
            }
        }
        /*
        // a bottom bar
        Row(modifier = Modifier
            .align(alignment = Alignment.BottomCenter)
            .height(topAndBottomBarHeight)
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.secondary)) {
            BottomInformationBar(newsViewModel)
        }
        */
    }
}

@Composable
fun TopBarTitle(numUnfinished: Int?) {
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(start = 60.dp, end = 60.dp)
            .fillMaxSize()) {
        val text = if (numUnfinished == 0) "ApplicationsInspector is done." else "ApplicationsInspector is analyzing your apps..."
        Text(text = text,
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center
        )
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