package tj.iskandar.guidebook.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import tj.iskandar.guidebook.viewmodel.GuideBookViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.asFlow
import androidx.lifecycle.distinctUntilChanged
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tj.iskandar.guidebook.navigation.NavigationGuide
import tj.iskandar.guidebook.navigation.Screen
import java.net.URLEncoder

//import androidx.compose.runtime.livedata.observeAsState

@SuppressLint("UnrememberedMutableState")
@Composable
fun MainScreen(navController:NavController,viewModel: GuideBookViewModel = hiltViewModel()) {
    val users by viewModel.posts.observeAsState(emptyList())

    val isLoading by viewModel.isLoading.collectAsState()
    val lazyListState = rememberLazyListState()
    val currentItems by remember { derivedStateOf { viewModel.currentItems } }
    LaunchedEffect(key1 = viewModel.hasMoreItems, key2 = isLoading, key3 = currentItems) {
        val lastVisibleItem = lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()
        if (lastVisibleItem != null && lastVisibleItem.index == currentItems.lastIndex && viewModel.hasMoreItems && !isLoading) {
            delay(100)
            viewModel.loadMoreItems()
        }
    }

    Scaffold() {
        Column(Modifier.fillMaxSize()) {

            Column {
                LazyColumn(state = lazyListState) {
                    itemsIndexed(items = users) { index, item ->
                        // Display the item
                        ItemBook(item = item, onClick = {
                            val urlEncode =
                                URLEncoder.encode("https://guidebook.com"+it, "utf-8")
                            val route = NavigationGuide.WebViewScreen.withArgs(urlEncode)
                            navController.navigate(route = route)
                        })
                        Spacer(modifier = Modifier.height(16.dp))
                        if (index == users.lastIndex - 1 && viewModel.hasMoreItems && !isLoading) {

                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }


                        }
                    }
                }

            }
        }
    }


}

