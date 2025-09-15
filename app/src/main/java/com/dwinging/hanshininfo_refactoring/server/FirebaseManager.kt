package com.dwinging.hanshininfo_refactoring.server

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings

object FirebaseManager {

    private lateinit var appContext: Context

    fun init(context: Context) {
        this.appContext = context.applicationContext
    }

    private val remoteConfig: FirebaseRemoteConfig by lazy {
        val appVersion = getAppVersion(appContext)
        val message = "새로운 버전이 출시했습니다.\n업데이트 후 다시 실행해 주세요"

        Firebase.remoteConfig.apply {
            val configSettings = remoteConfigSettings {
                minimumFetchIntervalInSeconds = 0
            }
            setConfigSettingsAsync(configSettings)
            setDefaultsAsync(mapOf(
                "latest_version" to appVersion,
                "update_message" to message
            ))
        }
    }
    private fun getAppVersion(context: Context): String? {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            "Unknown"
        }
    }

    fun fetchRemoteConfig(
        onComplete: (latestVersion: String, updateMessage: String) -> Unit
    ) {
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    val latestVersion = remoteConfig.getString("latest_version")
                    val message = remoteConfig.getString("update_message").replace("\\n", "\n")
                    onComplete(latestVersion, message)
                } else {
                    Log.w("FirebaseManager", "RemoteConfig fetch failed", task.exception)
                }
            }
    }

    fun checkAppVersion(latestVersion: String, context: Context): Boolean {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        val currentVersion = packageInfo.versionName.toString()
        return currentVersion == latestVersion
    }

    fun showUpdateDialog(context: Context, message: String) {
        AlertDialog.Builder(context)
            .setTitle("업데이트 알림")
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("확인") { _, _ ->
                // 앱 출시 후 변경 예정
//                val intent = Intent(
//                    Intent.ACTION_VIEW,
//                    "market://details?id=${context.packageName}".toUri()
//                )
//                context.startActivity(intent)

                val url = "https://drive.google.com/drive/folders/1sqtMPRcZzQbi9M04scbUVpoVIRJj8-We?usp=drive_link".toUri()
                val intent = Intent(Intent.ACTION_VIEW, url)
                context.startActivity(intent)
                (context as? Activity)?.finish()
            }
            .show()
    }
}