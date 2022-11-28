package com.example.testsecuritythierry.ui

import android.content.pm.PackageInfo
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.material.Divider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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
        /*
        state?.let {
            items(it.toList()) { item ->
                TableItemRow(item, applicationsInspectorViewModel)
            }
        }
        */
    }
}

@Composable
fun TableItemRow(item: PackageInfo, newsViewModel: NewsViewModel) {
    val numLetters = 25
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
                Text(text = item.packageName.take(numLetters))
                if (item.packageName.length > numLetters) {
                    Text(modifier = Modifier
                        .padding(start = horizontalMargin),
                        text = item.packageName.drop(numLetters).take(numLetters))
                }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxHeight()
                    .width(statusAreaWidth + 1.dp)) {
                Divider(
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .fillMaxHeight()
                        .width(1.dp)
                )
                Column(
                    modifier = Modifier.align(Alignment.CenterEnd)
                        .fillMaxHeight()
                        .width(statusAreaWidth)
                ) {
                    //ApplicationStatusReporter(item.packageName, newsViewModel)
                }
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
