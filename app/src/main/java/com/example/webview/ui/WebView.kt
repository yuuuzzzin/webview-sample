package com.example.webview.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.webview.AndroidBridge
import com.example.webview.MyWebChromeClient
import com.example.webview.MyWebViewClient
import kotlinx.coroutines.launch

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebView(modifier: Modifier = Modifier) {
    val activity = LocalView.current.context as Activity
    var webView: WebView? = null

    var bridge by remember { mutableStateOf<AndroidBridge?>(null) }
    var canGoBack by remember { mutableStateOf(false) }
    var isFullScreen by remember { mutableStateOf(false) }

    val myWebViewClient: WebViewClient =
        MyWebViewClient(
            doWhenVisitedHistoryUpdated = { view ->
                canGoBack = view?.canGoBack() == true
            },
        )

    val myWebChromeClient: WebChromeClient =
        MyWebChromeClient(
            activity = activity,
            onStartShowCustomView = {
                isFullScreen = true
            },
            onStartHideCustomView = {
                isFullScreen = false
            },
        )

    fun WebView.initBridge() {
        val androidBridge =
            AndroidBridge(
                context = this.context,
                webView = this,
            )

        bridge = androidBridge
        this.addJavascriptInterface(
            androidBridge,
            "AndroidBridge",
        )
    }

    LaunchedEffect(isFullScreen) {
        activity.requestedOrientation =
            if (isFullScreen) {
                ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            } else {
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
    }

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                layoutParams =
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                    )

                with(settings) {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                }

                webViewClient = myWebViewClient
                webChromeClient = myWebChromeClient
                initBridge()

                loadUrl("file:///android_asset/webview.html")
            }
        },
        update = {
            webView = it
        },
        modifier = modifier,
    )

    AlertTestButton(
        bridge = bridge,
    )

    BackHandler(enabled = canGoBack) {
        if (webView?.canGoBack() == true) {
            webView?.goBack()
        }
    }
}

@Composable
private fun AlertTestButton(bridge: AndroidBridge?) {
    val scope = rememberCoroutineScope()

    Button(
        onClick = {
            scope.launch {
                bridge?.showWebViewAlertWithCoroutine(
                    text = "안드로이드",
                )
            }
        },
        modifier =
            Modifier
                .padding(20.dp),
    ) {
        Text(
            text = "웹뷰 알럿 띄우기",
        )
    }
}
