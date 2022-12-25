package com.thesimply.thesimply

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dbHelper
import java.util.TreeMap

class KayitActivity : AppCompatActivity() {

    var accounts = TreeMap<String,String>()
    var accountmails = TreeMap<String,String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var db = FirebaseDatabase.getInstance().reference
        setContentView(R.layout.kayitolactivity)
        val geridon = findViewById<TextView>(R.id.txtGoToLogin)
        val txtAcc = findViewById<EditText>(R.id.txtKayitAcc)
        val txtPass = findViewById<EditText>(R.id.txtKayitPass)
        val txtPassAgain = findViewById<EditText>(R.id.txtKayitPassAgain)
        val txtMail = findViewById<EditText>(R.id.txtMail)
        geridon.setOnClickListener {
            val comeback = Intent(this, MainActivity::class.java)
            startActivity(comeback)
        }
        val veriekle = object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(i in snapshot.children)
                {
                    accounts[i.key.toString()] = i.child("txtPass").getValue().toString()
                    accountmails[i.key.toString()] = i.child("txtMail").getValue().toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                var builder = AlertDialog.Builder(this@KayitActivity)
                builder.setTitle("HATA!")
                builder.setMessage("Bir şeyler ters gitti. Lütfen internet bağlantını kontrol edip tekrar dene :)")
                builder.setPositiveButton("TAMAM") {dialog, id->
                    val comeback = Intent(this@KayitActivity, MainActivity::class.java)
                    startActivity(comeback)
                }
                builder.show()
            }
        }
        db.addValueEventListener(veriekle)
        val btnKayitOl = findViewById<Button>(R.id.btnKayitOl)
        btnKayitOl.setOnClickListener{
            try {
                if(txtAcc.getText().toString() == " " || txtAcc.getText().toString().length < 5 || txtAcc.getText().toString().length > 17)
                {
                    Toast.makeText(this,"Kullanıcı Adı, en az 5 en fazla 17 karakter olabilir.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                else if(txtPass.getText().toString() == " " || txtPass.getText().toString().length < 5 || txtPass.getText().toString().length > 17)
                {
                    Toast.makeText(this,"Şifre, en az 5 en fazla 17 karakter olabilir.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                else if(txtMail.getText().toString() == " " || txtMail.getText().toString().length < 5 || txtMail.getText().toString().length > 50)
                {
                    Toast.makeText(this,"Mail, en az 5 en fazla 50 karakter olabilir", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                else if(txtPass.getText().toString() != txtPassAgain.getText().toString())
                {
                    Toast.makeText(this,"Şifre ve şifre tekrarı birbiriyle uyuşmuyor.",Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                else if(!txtMail.getText().toString().contains("@") || !txtMail.getText().toString().contains(".com"))
                {
                    Toast.makeText(this,"Lütfen geçerli bir mail adresi girin..",Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                else if(txtAcc.getText().toString() == txtPass.getText().toString())
                {
                    Toast.makeText(this,"şifre ve kullanıcı adı aynı olamaz.",Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                for((i,v) in accounts)
                {
                    if(i.toString() == txtAcc.text.toString())
                    {
                        var builder = AlertDialog.Builder(this)
                        builder.setTitle("HATA!")
                        builder.setMessage("Bu kullanıcı adı zaten kullanılıyor.")
                        builder.setPositiveButton("TAMAM") {dialog, id->
                            txtAcc.setText("")
                        }
                        builder.show()
                        return@setOnClickListener
                    }
                }
                for((i,v) in accountmails)
                {
                    if(v.toString() == txtMail.text.toString())
                    {
                        var builder = AlertDialog.Builder(this)
                        builder.setTitle("HATA!")
                        builder.setMessage("Bu eposta zaten kullanılıyor.")
                        builder.setPositiveButton("TAMAM") {dialog, id->
                            txtMail.setText("")
                        }
                        builder.show()
                        return@setOnClickListener
                    }
                }
                db.child(txtAcc.text.toString()).setValue(dbHelper(txtPass.text.toString(),txtMail.text.toString()))
                accounts[txtAcc.text.toString()] = txtPass.text.toString()
                accountmails[txtAcc.text.toString()] = txtMail.text.toString()
                var builder = AlertDialog.Builder(this)
                builder.setTitle("Kayıt Oldun!")
                builder.setMessage("Başarıyla kayıt oldun! Artık aramıza girmeye hazırsın. Tebrikler!")
                builder.setPositiveButton("Devam!") {dialog, id->
                    var git = Intent(this,MainActivity::class.java)
                    startActivity(git)
                }
                builder.show()
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
    }
}