package com.pooja.minibank.core.utils

import android.view.View

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



