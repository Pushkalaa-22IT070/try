WhatsApp Msg

lateinit var pnoField: EditText


inside oncreate function
pnoField = findViewById(R.id.editTextTextMultiLine3)
val saveButton = findViewById<Button>(R.id.button)
saveButton.setOnClickListener {
    sendBookingConfirmation()
    sendSMSConfirmation()
}



private fun sendBookingConfirmation() {
    val phoneNumber = pnoField.text.toString().trim()
    val formattedPhone = phoneNumber.replace(Regex("[^0-9+]"), "")
    val message = "BOOKING CONFIRMED"
    val encodedMessage = URLEncoder.encode(message, "UTF-8")
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse("https://api.whatsapp.com/send?phone=$formattedPhone&text=$encodedMessage")
    startActivity(intent)
}

private fun sendSMSConfirmation() {
    val phoneNumber = pnoField.text.toString().trim()
    val message = "BOOKING CONFIRMED"
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse("sms:$phoneNumber?body=${Uri.encode(message)}")
    startActivity(intent)
}

AirplaneModeChangeReceiver.kt:

package com.example.god

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AirplaneModeChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val isenabled=intent?.getBooleanExtra("state",false) ?:return
        if(isenabled)
            Toast.makeText(context,"Enableddd",Toast.LENGTH_LONG).show()
        else
            Toast.makeText(context,"Disableddd",Toast.LENGTH_LONG).show()
    }
}


MainActivity.kt:

private lateinit var receiver: AirplaneModeChangeReceiver

val filter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
receiver=AirplaneModeChangeReceiver()
registerReceiver(receiver,filter)


BatteryReceiver.kt:

package com.example.god

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.widget.Toast

class BatteryReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        Toast.makeText(context, "Battery Level: $level%", Toast.LENGTH_LONG).show()
    }
}


MainActivity.kt:
private lateinit var batteryReceiver: BatteryReceiver

val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
batteryReceiver = BatteryReceiver()
registerReceiver(batteryReceiver, filter)

<uses-permission android:name="android.permission.BLUETOOTH" />
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
<uses-permission android:name="android.permission.BLUETOOTH_CONNECT" />
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />

package com.example.model1

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.Manifest
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Bundle
import android.provider.MediaStore
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Hardware : AppCompatActivity() {
    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private val CAMERA_PERMISSION_CODE = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hardware)
        val bluetoothtext = findViewById<TextView>(R.id.bluetoothtext)
        val btoggle = findViewById<TextView>(R.id.bluetoothbutton)
        fun bluetoothstatus() {
            bluetoothtext.text= when{
                bluetoothAdapter==null -> "Bluetooth not available"
                bluetoothAdapter.isEnabled -> "Bluetooth is off"
                else -> "Bluetooth is on"
            }
        }
        bluetoothstatus()

        btoggle.setOnClickListener {
            if (bluetoothAdapter != null) {
                if (bluetoothAdapter.isEnabled) {
                    bluetoothAdapter.disable()
                } else {
                    bluetoothAdapter.enable()
                }
                bluetoothstatus()
            }
        }

        val wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager

        val wifitext = findViewById<TextView>(R.id.wifitext)
        val wifibutton = findViewById<TextView>(R.id.wifibutton)

        fun wifistatus() {
            wifitext.text = if (wifiManager.isWifiEnabled) "WiFi is ON" else "WiFi is OFF"
        }

        wifistatus()

        wifibutton.setOnClickListener {
            wifiManager.isWifiEnabled = !wifiManager.isWifiEnabled
            wifistatus()
        }

        val cameratext = findViewById<TextView>(R.id.cameratext)
        val camerabutton = findViewById<TextView>(R.id.camerabutton)

        cameratext.text = "Camera ready"
        camerabutton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
            } else {
                openCamera()
            }
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(packageManager) != null) {
            startActivity(cameraIntent)
        } else {
            Toast.makeText(this, "No camera app found", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera()
        } else {
            Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }
}




