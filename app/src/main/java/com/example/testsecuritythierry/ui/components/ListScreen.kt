package com.example.testsecuritythierry.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import com.example.testsecuritythierry.data.models.DataNewsElement
import com.example.testsecuritythierry.ui.setup.RoutingScreen
import java.lang.Math.floor

val horizontalMargin = 20.dp
val rowHeight = 60.dp
const val goldenNumber = 1.618
val statusAreaWidth = (floor(goldenNumber * 60)).dp

@Composable
fun ListScreen(
    //newsViewModel: MainScreenNewsViewModel,
    stateListNews: LazyPagingItems<DataNewsElement>,
    navController: NavController,
) {
    // keep the scrolling state upon screen rotation
    val listState = rememberLazyListState()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.secondary
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = horizontalMargin),
        ) {
            // as stated at the link below, items(stateListNews.itemSnapshotList) does not work with AppConfig.pagingSize = 10, and total items = 48
            // but it works using the itemsIndex alternative
            // https://stackoverflow.com/questions/75960184/why-jetpack-compose-room-offline-pagination-not-loading-next-page
            //items(stateListNews.itemSnapshotList) {item ->
            //item?.let {
            //    TableItemRow(item)
            //}
            //}
            itemsIndexed(stateListNews.itemSnapshotList) { index, _ ->
                stateListNews[index]?.let { TableItemRow(it, index, navController) }
            }
        }
    }
}

@Composable
fun TableItemRow(item: DataNewsElement, index: Int, navController: NavController
) {
    Box(
        modifier = Modifier
            .height(rowHeight),
        contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.clickable {
                    navController.navigate(RoutingScreen.MyDetailScreen.route.replace("{rowId}", "$index"))
                }
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