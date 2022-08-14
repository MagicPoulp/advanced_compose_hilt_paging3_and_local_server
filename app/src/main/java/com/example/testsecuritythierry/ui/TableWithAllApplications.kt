package com.example.testsecuritythierry.ui

import android.content.pm.PackageInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.testsecuritythierry.viewmodels.ApplicationsInspectorViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val horizontalMargin = 20.dp

@Composable
fun TableWithAllApplications(
    applicationsInspectorViewModel: ApplicationsInspectorViewModel,
) {
    val state by applicationsInspectorViewModel.listPackages.observeAsState()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = horizontalMargin),
    ) {
        state?.let {
            items(it.toList()) { item ->
                TableItemRow(item)
            }
        }
    }
}

@Composable
fun TableItemRow(item: PackageInfo) {
    val numLetters = 25
    Box(
        modifier = Modifier
            .height(60.dp),
        contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxWidth()) {
            Text(text = item.packageName.take(numLetters))
            if (item.packageName.length > numLetters) {
                Text(modifier = Modifier
                    .padding(start = horizontalMargin),
                    text = item.packageName.drop(numLetters).take(numLetters))
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
