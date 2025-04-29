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