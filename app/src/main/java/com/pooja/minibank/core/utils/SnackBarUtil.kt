package com.pooja.minibank.core.utils

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.pooja.minibank.R

object SnackBarUtil {
    fun showSuccess(view : View, message : String)
    {
        val snackbar = Snackbar.make(view,message, Snackbar.LENGTH_LONG)
        snackbar.setBackgroundTint(
            ContextCompat.getColor(view.context, R.color.primary)
        )

        snackbar.setTextColor(
            ContextCompat.getColor(view.context,R.color.white)
        )

        snackbar.show()
    }

    fun showError(view : View, message : String?)
    {
        val finalMessage = message ?: "Something went wrong"
        val snackbar = Snackbar.make(view,finalMessage, Snackbar.LENGTH_LONG)
        snackbar.setBackgroundTint(
            ContextCompat.getColor(view.context, R.color.red)
        )

        val textView = snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.setTextColor(
            ContextCompat.getColor(view.context,R.color.white)
        )

        textView.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.ic_error, 0,0,0
        )

        textView.compoundDrawablePadding = 16

        snackbar.show()
    }


}