package com.thesimply.thesimply

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputBinding
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.TreeMap

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtKayitOl = findViewById<TextView>(R.id.txtCreateAcc)
        txtKayitOl.setOnClickListener{
            val kayit = Intent(this,KayitActivity::class.java)
            startActivity(kayit)
        }
    }
}