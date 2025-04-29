Alert,date and time picker:

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var btnAlert: Button
    lateinit var btnDate: Button
    lateinit var btnTime: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAlert = findViewById(R.id.btnAlert)
        btnDate = findViewById(R.id.btnDate)
        btnTime = findViewById(R.id.btnTime)

        // Alert Dialog
        btnAlert.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes") { _, _ ->
                    Toast.makeText(this, "You clicked Yes", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("No") { _, _ ->
                    Toast.makeText(this, "You clicked No", Toast.LENGTH_SHORT).show()
                }
                .show()
        }

        // Date Picker
        btnDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, { _, y, m, d ->
                Toast.makeText(this, "Date: $d/${m+1}/$y", Toast.LENGTH_SHORT).show()
            }, year, month, day)

            dpd.show()
        }

        // Time Picker
        btnTime.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            val tpd = TimePickerDialog(this, { _, h, m ->
                Toast.makeText(this, "Time: $h:$m", Toast.LENGTH_SHORT).show()
            }, hour, minute, true)

            tpd.show()
        }
    }
}

Progress bar:

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var progressBar: ProgressBar
    lateinit var btnStart: Button
    private val handler = Handler(Looper.getMainLooper())
    private var progressStatus = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)
        btnStart = findViewById(R.id.btnStart)

        btnStart.setOnClickListener {
            progressBar.visibility = ProgressBar.VISIBLE
            progressStatus = 0
            progressBar.progress = 0

            Thread {
                while (progressStatus < 100) {
                    progressStatus += 5
                    Thread.sleep(300)
                    handler.post {
                        progressBar.progress = progressStatus
                    }
                }
            }.start()
        }
    }
}

spinner:


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var spinnerProgress: ProgressBar
    lateinit var btnShowSpinner: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinnerProgress = findViewById(R.id.spinnerProgress)
        btnShowSpinner = findViewById(R.id.btnShowSpinner)

        btnShowSpinner.setOnClickListener {
            spinnerProgress.visibility = ProgressBar.VISIBLE

            // Automatically hide the spinner after 3 seconds
            Handler(Looper.getMainLooper()).postDelayed({
                spinnerProgress.visibility = ProgressBar.GONE
            }, 3000)
        }
    }
}

