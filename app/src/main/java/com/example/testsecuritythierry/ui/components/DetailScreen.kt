package com.example.testsecuritythierry.ui.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testsecuritythierry.ui.view_models.NewsViewModel

@Composable
fun DetailScreen(
    rowId: Int,
    newsViewModel: NewsViewModel = hiltViewModel(),
    //activity: MainActivity,
    //navController: NavController,
) {
    Text("Detail: " + rowId)
}
