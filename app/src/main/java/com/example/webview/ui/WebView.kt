package com.example.webview.ui

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.webview.MyWebViewClient
import com.example.webview.WebAppInterface

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebView(modifier: Modifier = Modifier) {
    var canGoBack by rememberSaveable { mutableStateOf(false) }
    var webView: WebView? = null

    val myWebViewClient: WebViewClient =
        MyWebViewClient(
            _doUpdateVisitedHistory = { view, _, _ ->
                canGoBack = view.canGoBack()
            },
        )

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
                webChromeClient = WebChromeClient()
                addJavascriptInterface(WebAppInterface(context), "Android")
                loadUrl("file:///android_asset/webview.html")
            }
        },
        update = {
            webView = it
        },
        modifier = modifier,
    )

    BackHandler(enabled = canGoBack) {
        if (webView?.canGoBack() == true) {
            webView?.goBack()
        }
    }
}
