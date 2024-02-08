package com.example.webview

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.widget.FrameLayout

class MyWebChromeClient(
    private val activity: Activity,
    private val onStartShowCustomView: () -> Unit,
    private val onStartHideCustomView: () -> Unit,
) : WebChromeClient() {
    private var customView: View? = null

    override fun onShowCustomView(
        view: View?,
        callback: CustomViewCallback?,
    ) {
        super.onShowCustomView(view, callback)
        onStartShowCustomView()

        if (customView != null) {
            onHideCustomView()
            return
        }

        customView = view
        (activity.window.decorView as FrameLayout).addView(
            customView,
            FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            ),
        )
    }

    override fun onHideCustomView() {
        super.onHideCustomView()
        onStartHideCustomView()
        (activity.window.decorView as FrameLayout).removeView(customView)
        customView = null
    }
}
