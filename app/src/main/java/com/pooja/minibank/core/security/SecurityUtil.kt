package com.pooja.minibank.core.security

object SecurityUtils {

    fun isDeviceRooted(): Boolean {
        return checkRootFiles() || checkSuBinary()
    }

    private fun checkRootFiles(): Boolean {
        val paths = arrayOf(
            "/system/app/Superuser.apk",
            "/system/xbin/su",
            "/system/bin/su",
            "/system/sbin/su",
            "/sbin/su",
            "/vendor/bin/su"
        )

        return paths.any { path ->
            try {
                java.io.File(path).exists()
            } catch (e: Exception) {
                false
            }
        }
    }

    private fun checkSuBinary(): Boolean {
        return try {
            val process = Runtime.getRuntime().exec(arrayOf("/system/xbin/which", "su"))
            val reader = java.io.BufferedReader(
                java.io.InputStreamReader(process.inputStream)
            )
            reader.readLine() != null
        } catch (e: Exception) {
            false
        }
    }
}