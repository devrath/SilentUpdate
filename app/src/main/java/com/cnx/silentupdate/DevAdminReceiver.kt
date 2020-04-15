package com.cnx.silentupdate

import android.app.admin.DeviceAdminReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class DevAdminReceiver : DeviceAdminReceiver() {
    override fun onEnabled(context: Context?, intent: Intent?) {
        super.onEnabled(context, intent)
        Log.d(TAG, "Device Owner Enabled")
        Toast.makeText(context,"Device Owner Enabled",Toast.LENGTH_LONG).show()
    }
}