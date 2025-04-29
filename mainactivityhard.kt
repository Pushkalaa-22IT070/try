Notification:

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private val CHANNEL_ID = "my_channel_id"
    private val NOTIFICATION_ID = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel()

        val notifyButton: Button = findViewById(R.id.notifyButton)
        notifyButton.setOnClickListener {
            sendNotification()
        }
    }

    private fun sendNotification() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Hello!")
            .setContentText("This is a simple notification.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "MyChannel"
            val descriptionText = "Simple Notification Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />


Geocoder:

import android.location.Geocoder
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var edtAddress: EditText
    lateinit var btnGetCoordinates: Button
    lateinit var txtResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtAddress = findViewById(R.id.edtAddress)
        btnGetCoordinates = findViewById(R.id.btnGetCoordinates)
        txtResult = findViewById(R.id.txtResult)

        btnGetCoordinates.setOnClickListener {
            val address = edtAddress.text.toString()
            val geocoder = Geocoder(this, Locale.getDefault())
            val addresses = geocoder.getFromLocationName(address, 1)

            if (addresses != null && addresses.isNotEmpty()) {
                val location = addresses[0]
                txtResult.text = "Latitude: ${location.latitude}, Longitude: ${location.longitude}"
            } else {
                txtResult.text = "Location not found"
            }
        }
    }
}

<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />

Shared prefe:

import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var edtName: EditText
    lateinit var btnSave: Button
    lateinit var btnShow: Button
    lateinit var txtResult: TextView

    val PREFS_NAME = "MyPrefs"
    val KEY_NAME = "user_name"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtName = findViewById(R.id.edtName)
        btnSave = findViewById(R.id.btnSave)
        btnShow = findViewById(R.id.btnShow)
        txtResult = findViewById(R.id.txtResult)

        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        btnSave.setOnClickListener {
            val name = edtName.text.toString()
            val editor = sharedPreferences.edit()
            editor.putString(KEY_NAME, name)
            editor.apply()
            Toast.makeText(this, "Name Saved!", Toast.LENGTH_SHORT).show()
        }

        btnShow.setOnClickListener {
            val savedName = sharedPreferences.getString(KEY_NAME, "No name found")
            txtResult.text = "Saved Name: $savedName"
        }
    }
}

android:hint="enter"  //edittext