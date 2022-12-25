package com.thesimply.thesimply

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.service.textservice.SpellCheckerService.Session
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class SifreUActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sifreuactivity)
        val don = findViewById<TextView>(R.id.txtGiriseDon)
        don.setOnClickListener{
            val don = Intent(this,MainActivity::class.java)
            startActivity(don)
        }
        val kodgonder = findViewById(R.id.btnSaveKodG) as Button
        kodgonder.setOnClickListener{

        }
    }
}