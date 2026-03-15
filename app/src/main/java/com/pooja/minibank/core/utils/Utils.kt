package com.pooja.minibank.core.utils

import android.app.Activity
import android.view.View
import android.view.WindowManager

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

 fun View.animate() {
    this.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).withEndAction {
        this.animate().scaleX(1f).scaleY(1f)
    }
}

fun Activity.enableSecureScreen()
{
    fun Activity.enableSecureScreen() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
    }
}