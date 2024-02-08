package com.example.webview.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.webview.ui.theme.WebviewSampleTheme

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen() {
    WebviewSampleTheme {
        Box(
            modifier =
                Modifier
                    .background(
                        color = MaterialTheme.colorScheme.background,
                    )
                    .fillMaxSize(),
        ) {
            WebView(
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
