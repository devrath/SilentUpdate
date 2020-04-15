package com.cnx.silentupdate

import android.Manifest
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.UserManager
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*

import kotlinx.android.synthetic.main.content_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            // Clicking on install button will cause the app-update.apk to
            // be installed from the given location.
            // You need to ensure that the location where you put the APK file
            // with updated version is readable from this app.
            try {

                val file = File("/data/local/tmp/test.apk")

                if(file.exists()){
                    install(this@MainActivity, packageName, "/data/local/tmp/test.apk")
                }else{
                    Toast.makeText(this,"File is not available", Toast.LENGTH_LONG).show()
                }


                //Runtime.getRuntime().exec("dpm set-device-owner com.cnx.silentupdate/.DevAdminReceiver");

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onResume() {
        super.onResume()
        val dpm = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        if (dpm.isDeviceOwnerApp(packageName)){
            Log.d(TAG, "is device owner")
            Toast.makeText(this,"is device owner", Toast.LENGTH_LONG).show()
        }else{
            Log.d(TAG, "not device owner")
            Toast.makeText(this,"not device owner", Toast.LENGTH_LONG).show()
        }

        tv_text.text = "version code=" + BuildConfig.VERSION_CODE
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun grantStoragePermission() {
        val dpm = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val cn = ComponentName(this, DevAdminReceiver::class.java)

        dpm.setPermissionGrantState(cn, packageName,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            DevicePolicyManager.PERMISSION_GRANT_STATE_GRANTED)
    }


    companion object {
        val TAG = "MinDeviceOwner"
    }
}