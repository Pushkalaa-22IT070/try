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





