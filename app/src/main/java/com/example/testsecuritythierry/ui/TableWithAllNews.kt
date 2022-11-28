package com.example.testsecuritythierry.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.material.Divider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.testsecuritythierry.models.DataNewsElement

import com.example.testsecuritythierry.viewmodels.NewsViewModel
import java.lang.Math.floor

val horizontalMargin = 20.dp
val rowHeight = 60.dp
const val goldenNumber = 1.618
val statusAreaWidth = (floor(goldenNumber * 60)).dp

@Composable
fun TableWithAllNews(
    newsViewModel: NewsViewModel,
) {
    val state by newsViewModel.listNews.observeAsState()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = horizontalMargin),
    ) {
        state?.let {
            items(it.toList()) { item ->
                TableItemRow(item, newsViewModel)
            }
        }
    }
}

@Composable
fun TableItemRow(item: DataNewsElement, newsViewModel: NewsViewModel) {
    Box(
        modifier = Modifier
            .height(rowHeight),
        contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            Column(
                //modifier = Modifier.align(Alignment.Start)
            ) {
                item.titre?.let { Text(text = it) }
            }
        }
        Divider(
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(1.dp)
        )
    }
}
