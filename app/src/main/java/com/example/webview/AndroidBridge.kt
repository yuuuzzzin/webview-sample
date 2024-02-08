package com.example.webview

import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Toast
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AndroidBridge(
    private val context: Context,
    private val webView: WebView,
) {
    @JavascriptInterface
    fun showToast(toast: String) {
        Toast.makeText(context, toast, Toast.LENGTH_LONG).show()
    }

    suspend fun showWebViewAlertWithCoroutine(text: String) {
        val script = "window.showWebViewAlert('$text');"
        val result = evaluateWebViewFunctionWithCoroutine(script)
        Log.d("tag_test", result)
    }

    fun showWebViewAlert(text: String) {
        val script = "window.showWebViewAlert('$text');"
        evaluateWebViewFunction(script)
    }

    private suspend fun evaluateWebViewFunctionWithCoroutine(script: String): String {
        return suspendCoroutine { cont ->
            webView.evaluateJavascript(script) { result ->
                cont.resume(result)
            }
        }
    }

    private fun evaluateWebViewFunction(
        script: String,
        callback: ((String) -> Unit)? = null,
    ) {
        return webView.evaluateJavascript(script, callback)
    }
}
