package com.example.testsecuritythierry.ui.components

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.testsecuritythierry.R
import com.example.testsecuritythierry.ui.MainActivity
import com.example.testsecuritythierry.ui.view_models.MainScreenNewsViewModel
import com.example.testsecuritythierry.ui.view_models.UiState

@Composable
fun MainScreen(newsViewModel: MainScreenNewsViewModel = hiltViewModel(),
               activity: MainActivity
) {
    //val numUnfinished by newsViewModel.numUnfinished.observeAsState()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val topAndBottomBarHeight = 80.dp
    newsViewModel.init(
        unexpectedServerDataErrorString = activity.resources.getString(R.string.unexpected_server_data)
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
            val state by newsViewModel.uiState.collectAsStateWithLifecycle()
            val stateListNews = newsViewModel.listNews.collectAsLazyPagingItems()
            when (state) {
                UiState.Filled -> TableWithAllNews(stateListNews)
                else -> Row {
                    ProgressIndicator()
                }
            }

            // LazyPagingItems cannot collected in the ViewModel, but it can be in a LaunchedEffect
            // https://developer.android.com/jetpack/compose/side-effects#snapshotFlow
            LaunchedEffect(state) {
                snapshotFlow { stateListNews.itemSnapshotList.count() }
                    .collect {
                        val newState = when (it) {
                            0 -> UiState.Empty
                            else -> UiState.Filled
                        }
                        newsViewModel.setUiState(newState)
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