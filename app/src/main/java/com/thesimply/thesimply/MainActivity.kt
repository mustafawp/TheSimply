package com.thesimply.thesimply

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputBinding
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.core.utilities.Tree
import java.util.TreeMap

class MainActivity : AppCompatActivity() {

    var accounts = TreeMap<String,String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val txtKayitOl = findViewById<TextView>(R.id.txtCreateAcc)
        txtKayitOl.setOnClickListener{
            val kayit = Intent(this,KayitActivity::class.java)
            startActivity(kayit)
        }
        var db = FirebaseDatabase.getInstance().reference
        val veriekle = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(i in snapshot.children)
                {
                    accounts[i.key.toString()] = i.child("txtPass").getValue().toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                var builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("HATA!")
                builder.setMessage("Bir şeyler ters gitti. Lütfen internet bağlantını kontrol edip tekrar dene :)")
                builder.setPositiveButton("TAMAM") {dialog, id->
                    val comeback = Intent(this@MainActivity, MainActivity::class.java)
                    startActivity(comeback)
                }
                builder.show()
            }
        }
        db.addValueEventListener(veriekle)
        val btngiris = findViewById<Button>(R.id.btnGirisYap)
        btngiris.setOnClickListener{
            try {
                val acc = findViewById<EditText>(R.id.txtAcc)
                val pass = findViewById<EditText>(R.id.txtPass)
                var f = false
                for ((i,v) in accounts)
                {
                    if(i.toString() == acc.text.toString())
                    {
                        if(v.toString() == pass.text.toString())
                        {
                            Toast.makeText(this,"Başarıyla " + i.toString() + " olarak giriş yaptın!",Toast.LENGTH_LONG).show()
                            var bilgi = findViewById<TextView>(R.id.txtBilgi)
                            bilgi.setText("Hey! Seni burada görmek güzel.")
                            bilgi.setTextColor(Color.parseColor("#ffffff"))
                            return@setOnClickListener
                        }
                        else
                        {
                            var bilgi = findViewById<TextView>(R.id.txtBilgi)
                            bilgi.setText("Hatalı şifre girdiniz!")
                            bilgi.setTextColor(Color.parseColor("#ff0000"))
                            return@setOnClickListener
                        }
                    }
                }
                Toast.makeText(this,"Böyle bir hesap bulunamadı.",Toast.LENGTH_LONG).show()

            }
            catch (e: Exception)
            {
                var builder = AlertDialog.Builder(this)
                builder.setTitle("HATA!")
                builder.setMessage("Bir şeyler ters gitti. Lütfen her şeyi doğru yaptığına emin olup, tekrar dene :)")
                builder.setPositiveButton("TAMAM") {dialog, id-> }
                builder.show()
            }
        }
        val fpass = findViewById(R.id.txtForgetPass) as TextView
        fpass.setOnClickListener{
            val git = Intent(this,SifreUActivity::class.java)
            startActivity(git)
        }
    }
}