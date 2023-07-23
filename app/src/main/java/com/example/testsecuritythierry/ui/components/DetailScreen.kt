package com.example.testsecuritythierry.ui.components

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import androidx.paging.compose.LazyPagingItems
import com.example.testsecuritythierry.data.models.DataNewsElement

@Composable
fun DetailScreen(
    stateListNews: LazyPagingItems<DataNewsElement>,
    rowId: Int,
) {
    if (rowId > stateListNews.itemCount - 1 ) {
        CenterAlignedText("Error")
    }
    else
    {
        val itemData = stateListNews.itemSnapshotList[rowId]
        val html = itemData?.html
        html?.let {
            AndroidView(
                factory = {
                    WebView(it).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        webViewClient = WebViewClient()
                        loadData(html, "text/html", "UTF-8")
                    }
                }
            )
        } ?: run {
            CenterAlignedText("Missing HTML, please try another row.")
        }
    }
}

@Composable
fun CenterAlignedText(text: String) {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Text(text = text,
            textAlign = TextAlign.Center)
    }
}
