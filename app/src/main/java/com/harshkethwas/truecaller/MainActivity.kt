package com.harshkethwas.truecaller

import android.app.Activity
import android.app.role.RoleManager
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.harshkethwas.truecaller.databinding.ActivityMainBinding
import android.provider.BlockedNumberContract.BlockedNumbers
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.getSystemService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
   private val blocklistRef = FirebaseDatabase.getInstance().getReference("blocklist")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(call_logs1())

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav)
        bottomNavigationView.itemIconTintList = null

        binding.nav.setOnItemSelectedListener {

            when(it.itemId){

                R.id.Calls -> replaceFragment(call_logs1())
                R.id.contacts -> replaceFragment(contacts1())
                R.id.report -> replaceFragment(report1())
                R.id.profile -> replaceFragment(profile1())

                else ->{
                }
            }
            true
        }

        val roleManager = getSystemService(Context.ROLE_SERVICE) as RoleManager
        val setDefaultDialerLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            fun(result: ActivityResult) {

                if (result.resultCode == Activity.RESULT_OK) {
                    // Check if your app is now the default dialer

                    if (roleManager.isRoleHeld(RoleManager.ROLE_DIALER)) {
                        // Your app is now the default dialer
                        Toast.makeText(this, "This app is now set as default dialer", Toast.LENGTH_SHORT).show()
                        //navigate to dashboard like screen here
                    } else {
                        // Your app is not the default dialer
                        Toast.makeText(this, "Please set this app as default calling app to proceed", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Failed to set your app as the default dialer
                    Log.d("Dialer", "something went wrong while setting as default, most likely user cancelled the popup")
                    Toast.makeText(this, "Please set this app as default calling app to proceed", Toast.LENGTH_SHORT).show()
                }
            }
        )

        val intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_DIALER)
        setDefaultDialerLauncher.launch(intent)






onResume()

    }

    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,fragment)
        fragmentTransaction.commit()

    }

    override fun onResume() {
        super.onResume()


        /*
        // Listen for changes to the blocklist node. This code is for blockiung multiple numbers at once
        blocklistRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Get the list of blocked numbers.
                val blockedNumbers = snapshot.getValue(List::class.java).toString()

                // For each blocked number, check if the incoming call is from that number.
                if (blockedNumbers != null) {
                    for (blockedNumber in blockedNumbers) {

                        val uri = Uri.parse(BlockedNumbers.CONTENT_URI.toString())
                        val resolver = contentResolver
                        resolver.delete(uri, null, null)

                        val values = ContentValues()
                        values.put(BlockedNumbers.COLUMN_ORIGINAL_NUMBER,"+917222947074")

                        resolver.insert(uri, values)
                    }
                }
                }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Error", "Error occurred while listening for changes to the blocklist node.")
            }
        })
        }*/
        val uri = Uri.parse(BlockedNumbers.CONTENT_URI.toString())
        val resolver = contentResolver
        resolver.delete(uri, null, null)

        val values = ContentValues()
        values.put(BlockedNumbers.COLUMN_ORIGINAL_NUMBER, "+917222947074")
        resolver.insert(uri, values)
    }
    }












