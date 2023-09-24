package com.harshkethwas.truecaller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.os.postDelayed

class splash : AppCompatActivity() {
    lateinit var  handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
handler =Handler()

        handler.postDelayed(3000){
            startActivity(Intent(this,login::class.java))
        finish()
        }
    }
}