package com.example.testsecuritythierry.ui

import android.content.pm.PackageInfo
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
import com.example.testsecuritythierry.models.AnalysisResultError
import com.example.testsecuritythierry.models.AnalysisResultNoThreat
import com.example.testsecuritythierry.models.AnalysisResultPending
import com.example.testsecuritythierry.models.AnalysisResultVirusFound
import java.lang.Math.floor

val horizontalMargin = 20.dp
val rowHeight = 60.dp
const val goldenNumber = 1.618
val statusAreaWidth = (floor(goldenNumber * 60)).dp

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
                TableItemRow(item, applicationsInspectorViewModel)
            }
        }
    }
}

@Composable
fun TableItemRow(item: PackageInfo, applicationsInspectorViewModel: ApplicationsInspectorViewModel) {
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
                    ApplicationStatusReporter(item.packageName, applicationsInspectorViewModel)
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

@Composable
fun ApplicationStatusReporter(packageName: String, applicationsInspectorViewModel: ApplicationsInspectorViewModel) {
    val itemLiveData = applicationsInspectorViewModel.mapAppToVirusStatus[packageName]
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()) {
            if (itemLiveData == null) {
                Text("Pending", color = Color.Black)
            } else {
                val state by itemLiveData.observeAsState()
                when (state) {
                    is AnalysisResultVirusFound -> Text("Virus", color = Color.Red)
                    is AnalysisResultError -> Text("Error", color = Color.Blue)
                    is AnalysisResultNoThreat -> Text("No threat", color = Color(0xFF1E821E))
                    is AnalysisResultPending -> Text("Pending", color = Color.Black)
                }
            }
    }
}