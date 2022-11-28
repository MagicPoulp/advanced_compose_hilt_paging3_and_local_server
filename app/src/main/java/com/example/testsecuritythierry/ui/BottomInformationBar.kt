package com.example.testsecuritythierry.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/*
@Composable
fun BottomInformationBar(applicationsInspectorViewModel: ApplicationsInspectorViewModel) {
    val numInfected by applicationsInspectorViewModel.numViruses.observeAsState()
    val numUnfinished by applicationsInspectorViewModel.numUnfinished.observeAsState()
    val listPackages by applicationsInspectorViewModel.listPackages.observeAsState()
    val numApps = listPackages?.size
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()) {
        if (numUnfinished == -1) {
            Text("")
        } else {
            // top text
            if (numUnfinished == 0) {
                Text(
                    text = "Analysis finished."
                )
            } else {
                Text(
                    text = "Unfinished inspections: $numUnfinished/$numApps"
                )
            }
            // bottom text
            if (numInfected != 0) {
                Text(
                    text = "Infected device. $numInfected/$numApps apps are infected.",
                    color = Color.Red
                )
            } else {
                if (numUnfinished == 0) {
                    Text(
                        text = "No threat found in $numApps apps.",
                    )
                } else {
                    Text(
                        text = "",
                    )
                }
            }
        }
    }
}
*/