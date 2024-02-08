package com.example.webview

import android.webkit.WebView
import android.webkit.WebViewClient

class MyWebViewClient(
    private val doWhenVisitedHistoryUpdated: ((view: WebView?) -> Unit)? = null,
) : WebViewClient() {
    override fun doUpdateVisitedHistory(
        view: WebView?,
        url: String?,
        isReload: Boolean,
    ) {
        super.doUpdateVisitedHistory(view, url, isReload)
        doWhenVisitedHistoryUpdated?.invoke(view)
    }
}
