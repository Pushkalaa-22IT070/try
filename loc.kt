TO DISPLAY CURRENT LOCATION

Add the below in manifest file

<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

Add the below in build.gradle.kts(:app)(insdie dependencies)

implementation("com.google.android.gms:play-services-location:18.0.0")


val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
val button = findViewById<Button>(R.id.button)

button.setOnClickListener {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val latitude = location.latitude
                val longitude = location.longitude
                val geocoder = Geocoder(this, Locale.getDefault())
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    val address = addresses[0].getAddressLine(0)
                    Toast.makeText(this, "Your Address:\n$address", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}




GIVEN: latitude AND longitude...DISPLAY Address

val button = findViewById<Button>(R.id.button)
button.setOnClickListener {
    val latitude = 13.0827
    val longitude = 80.2707

    val geocoder = Geocoder(this, Locale.getDefault())
    val addresses = geocoder.getFromLocation(latitude, longitude, 1)

    if (addresses != null && addresses.isNotEmpty()) {
        val address = addresses[0].getAddressLine(0)
        Toast.makeText(this, "Address: $address", Toast.LENGTH_LONG).show()
    }

}


GIVEN: Address...DISPLAY latitude and longitude

val button = findViewById<Button>(R.id.button)
button.setOnClickListener {
    val addressString = "Chennai, India"
    val geocoder = Geocoder(this, Locale.getDefault())
    val addresses = geocoder.getFromLocationName(addressString, 1)
    if (addresses != null && addresses.isNotEmpty()) {
        val address = addresses[0]
        val latitude = address.latitude
        val longitude = address.longitude
        Toast.makeText(this, "Latitude: $latitude, Longitude: $longitude", Toast.LENGTH_LONG).show()
    } else {
        Toast.makeText(this, "No address found", Toast.LENGTH_SHORT).show()
    }

}

//fb-pgrb
package com.example.mul1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Firebase Realtime Database reference
        val database = Firebase.database
        val myRef = database.getReference("users")

        // UI elements
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val userData = mapOf(
                    "email" to email,
                    "password" to password
                )

                myRef.push().setValue(userData)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Data saved to Firebase ", Toast.LENGTH_SHORT).show()
                        emailEditText.text.clear()
                        passwordEditText.text.clear()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed to save ", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Fill all fields ", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
xmlns:android="http://schemas.android.com/apk/res/android"
android:orientation="vertical"
android:padding="24dp"
android:layout_width="match_parent"
android:layout_height="match_parent">

<EditText
android:id="@+id/emailEditText"
android:hint="Email"
android:inputType="textEmailAddress"
android:layout_width="match_parent"
android:layout_height="wrap_content"/>

<EditText
android:id="@+id/passwordEditText"
android:hint="Password"
android:inputType="textPassword"
android:layout_width="match_parent"
android:layout_height="wrap_content"/>

<Button
android:id="@+id/loginButton"
android:text="Login"
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:layout_marginTop="16dp"/>

</LinearLayout>
