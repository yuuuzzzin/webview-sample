package com.example.webview

import android.webkit.WebView
import android.webkit.WebViewClient

class MyWebViewClient(
    private val _doUpdateVisitedHistory: ((view: WebView, url: String?, isReload: Boolean) -> Unit)? = null,
) : WebViewClient() {
    override fun doUpdateVisitedHistory(
        view: WebView,
        url: String?,
        isReload: Boolean,
    ) {
        _doUpdateVisitedHistory?.invoke(
            view,
            url,
            isReload,
        ) ?: super.doUpdateVisitedHistory(
            view,
            url,
            isReload,
        )
    }
}
