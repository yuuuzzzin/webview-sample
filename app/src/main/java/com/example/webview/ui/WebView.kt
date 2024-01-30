package com.example.webview.ui

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.webview.WebAppInterface

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebView(modifier: Modifier = Modifier) {
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

                webViewClient = WebViewClient()
                webChromeClient = WebChromeClient()
                addJavascriptInterface(WebAppInterface(context), "Android")
                loadUrl("file:///android_asset/webview.html")
            }
        },
        modifier = modifier,
    )
}
