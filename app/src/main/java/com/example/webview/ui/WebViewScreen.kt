package com.example.webview.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.webview.ui.theme.WebviewSampleTheme

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen() {
    WebviewSampleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            WebView(
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
