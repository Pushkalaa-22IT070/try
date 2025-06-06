activity_main.xml:
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto"
xmlns:tools="http://schemas.android.com/tools"
android:id="@+id/main"
android:layout_width="match_parent"
android:layout_height="match_parent"
tools:context=".MainActivity">


<EditText
android:id="@+id/editTextTextMultiLine"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:ems="10"
android:gravity="start|top"
android:inputType="textMultiLine"
android:hint="Enter name"
app:layout_constraintBottom_toBottomOf="parent"
app:layout_constraintEnd_toEndOf="parent"
app:layout_constraintHorizontal_bias="0.154"
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintTop_toTopOf="parent"
app:layout_constraintVertical_bias="0.128" />

<EditText
android:id="@+id/editTextTextMultiLine2"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:ems="10"
android:gravity="start|top"
android:inputType="textMultiLine"
android:hint="Enter Email"
app:layout_constraintBottom_toBottomOf="parent"
app:layout_constraintEnd_toEndOf="parent"
app:layout_constraintHorizontal_bias="0.154"
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintTop_toTopOf="parent"
app:layout_constraintVertical_bias="0.24" />

<Button
android:id="@+id/button"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text="Save"
app:layout_constraintBottom_toBottomOf="parent"
app:layout_constraintEnd_toEndOf="parent"
app:layout_constraintHorizontal_bias="0.424"
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintTop_toTopOf="parent"
app:layout_constraintVertical_bias="0.373" />

<ListView
android:id="@+id/listView"
android:layout_width="389dp"
android:layout_height="405dp"
app:layout_constraintBottom_toBottomOf="parent"
app:layout_constraintEnd_toEndOf="parent"
app:layout_constraintHorizontal_bias="0.272"
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintTop_toTopOf="parent"
app:layout_constraintVertical_bias="1.0" />
</androidx.constraintlayout.widget.ConstraintLayout>



MainActivity.kt:
package com.example.god

import DatabaseHelper
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var dbHelper: DatabaseHelper
    lateinit var adapter: ArrayAdapter<String>
    lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val nameField = findViewById<EditText>(R.id.editTextTextMultiLine)
        val emailField = findViewById<EditText>(R.id.editTextTextMultiLine2)
        val saveButton = findViewById<Button>(R.id.button)
        listView = findViewById(R.id.listView)

        dbHelper = DatabaseHelper(this)
        loadList()

        saveButton.setOnClickListener {
            val name = nameField.text.toString()
            val email = emailField.text.toString()
            dbHelper.insertUser(name, email)
            nameField.text.clear()
            emailField.text.clear()
            loadList()
        }
    }
    private fun loadList() {
        val userList = dbHelper.getAllUsers()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, userList)
        listView.adapter = adapter
    }
}



DatabaseHelper.kt:
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "UserDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE UserDetails(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS UserDetails")
        onCreate(db)
    }

    fun insertUser(name: String, email: String) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("name", name)
        values.put("email", email)
        db.insert("UserDetails", null, values)
    }

    fun getAllUsers(): List<String> {
        val userList = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM UserDetails", null)

        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
                userList.add("$name - $email")
            } while (cursor.moveToNext())
        }

        cursor.close()
        return userList
    }

}
