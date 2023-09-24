package com.harshkethwas.truecaller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class Registration : AppCompatActivity() {

    private var mAuth: FirebaseAuth?=null


    private var mFirebaseDatabaseInstances: FirebaseDatabase?=null
    private lateinit  var mFirebase: DatabaseReference

    //Creating member variable for userId and emailAddress

    private lateinit var nProgressBar : ProgressBar
    private  lateinit var rusername:EditText
    private  lateinit var remail:EditText
    private  lateinit var rstate:EditText
    private  lateinit var rnumber:EditText
    private  lateinit var rButton: Button
    override  fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        mAuth=FirebaseAuth.getInstance()
        //Get instance of FirebaseDatabase
        mFirebaseDatabaseInstances= FirebaseDatabase.getInstance()
        //if already logged in go to sign in screen

        nProgressBar =findViewById(R.id.progressBar2)
        rusername =findViewById(R.id.et_name)
        remail =findViewById(R.id.et_email)
        rstate =findViewById(R.id.et_state)
        rnumber =findViewById(R.id.et_number)
        rButton =findViewById(R.id.et_btn)







        //calling onRegisterClicked button
        rButton.setOnClickListener{
            //Validation checking


            if(TextUtils.isEmpty(rusername.toString())){
                Toast.makeText(applicationContext,"Enter Username!",Toast.LENGTH_LONG).show()
            }
            if(TextUtils.isEmpty(remail.toString())){
                Toast.makeText(applicationContext,"Enter email address!",Toast.LENGTH_LONG).show()
            }
            if(TextUtils.isEmpty(rstate.toString())){
                Toast.makeText(applicationContext,"Enter State!",Toast.LENGTH_LONG).show()
            }


            if(rnumber.toString().length<10){
                Toast.makeText(applicationContext,"Number is short",Toast.LENGTH_LONG).show()
            }

            //Making progressBar visible
            nProgressBar = findViewById(R.id.progressBar2)
            nProgressBar!!.visibility=View.VISIBLE



            val username = rusername.text.toString()
            val usernumber = rnumber.text.toString()
            val state = rstate.text.toString()
            val email = remail.text.toString()
            mFirebase =FirebaseDatabase.getInstance().getReference("Registrations")


            val registration = register(username,usernumber,state,email)
            mFirebase.child(usernumber).setValue(registration).addOnSuccessListener{
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
                startActivity(Intent(this,MainActivity::class.java))
                finish()

            }
        }
    }
}


