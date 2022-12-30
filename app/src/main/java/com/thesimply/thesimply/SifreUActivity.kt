package com.thesimply.thesimply

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.apache.commons.mail.DefaultAuthenticator
import org.apache.commons.mail.Email
import org.apache.commons.mail.SimpleEmail


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
            var random = (1..1000).random()
            Log.e("deneme","gelio 1")
            var email: Email = SimpleEmail()
            email.hostName = "smtp.gmail.com"
            email.setSmtpPort(587)
            email.isSSLOnConnect = false
            email.setAuthenticator(
                DefaultAuthenticator(
                    "thesimplymusicplatform@gmail.com",
                    "taehyungvbambam123"
                )
            )
            email.setFrom("thesimplymusicplatform@gmail.com")
            email.addTo("burakissues@gmail.com")
            email.subject = "The Simply: Music Platform"
            email.setMsg("İşte E-Posta 'nı onaylaman için kodun: "+random)
            email.send()
            //sendEmail("burakissues@gmail.com","E-Posta Onaylama","Merhaba! E-Postanızı onaylamak için bu kodu uygulamamıza girin: "+random)
        }
    }
}
