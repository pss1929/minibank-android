package com.pooja.minibank.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.pooja.minibank.R
import com.pooja.minibank.data.remote.interceptor.SessionManager
import com.pooja.minibank.ui.auth.LoginActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()

        SessionManager.logoutListener = {

            runOnUiThread {
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.session_expired))
                    .setMessage("Your session has expired, Please login again.")
                    .setCancelable(false)
                    .setPositiveButton("Login"){_,_ ->
                        startActivity(Intent(this, LoginActivity::class.java))
                        finishAffinity()
                    }.show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStop() {
        super.onStop()
        SessionManager.logoutListener = null
    }
}