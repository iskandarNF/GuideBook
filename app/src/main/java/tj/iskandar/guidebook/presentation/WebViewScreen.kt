package tj.iskandar.guidebook.presentation

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import tj.iskandar.guidebook.R


@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WebViewScreen(navController: NavController,url: String?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        val colorBack = remember { mutableStateOf(Color(0xFF4CAF50)) }
        var backEnabled by remember { mutableStateOf(false) }

        var webView: WebView? = null
        Log.e("TAG", "URLWeb: "+url )

        val webViewChromeClient = object:WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {

                super.onProgressChanged(view, newProgress)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .background(color = Color(0xFFFAFAFA)),
        ){
            Row(
                modifier = Modifier

                    .wrapContentWidth()
                    .height(54.dp)
                    .background(Color(0xFFFAFAFA)),
//                        .clickable { navController.navigateUp() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                    "backIcon",
                    modifier = Modifier
                        .padding(top = 2.dp, start = 8.dp, end = 8.dp)
                        .width(15.dp)
                        .height(15.dp), alignment = Alignment.Center
                )
                Text(

                    text = "Back",
                    color = colorBack.value,
                    textAlign = TextAlign.Center,

                )
            }
        }
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()) {
            AndroidView(
                factory = {
                    WebView(it).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        webViewClient = object : WebViewClient() {
                            override fun shouldOverrideUrlLoading(
                                view: WebView?,
                                request: WebResourceRequest?
                            ): Boolean {
                                return false
                            }
                        }
                        settings.javaScriptEnabled = true
                        settings.javaScriptCanOpenWindowsAutomatically=true
                        settings.loadWithOverviewMode=true
                        settings.domStorageEnabled=true
                        settings.builtInZoomControls=true
                        settings.displayZoomControls=true
                        settings.databaseEnabled=true
                        settings.loadsImagesAutomatically=true
//                        settings.builtInZoomControls=true
//                        settings.useWideViewPort=true
                    }

                }, update = {
                    it.loadUrl("$url")
                    it.settings.javaScriptEnabled=true
                    it.settings.javaScriptCanOpenWindowsAutomatically=true
                    it.settings.loadWithOverviewMode=true
                    it.settings.domStorageEnabled=true
                    it.settings.builtInZoomControls=true
                    it.settings.displayZoomControls=true
                    it.settings.databaseEnabled=true
                    it.settings.loadsImagesAutomatically=true
//                    it.settings.builtInZoomControls=true
//                    it.settings.useWideViewPort=true

//                    it.settings.useWideViewPort=true
                    it.settings.supportZoom()

                }
            )

        }
    }
}