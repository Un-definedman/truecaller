package com.harshkethwas.truecaller

import android.content.ContentValues.TAG

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class report1 : Fragment() {
    private var mAuth: FirebaseAuth? = null
    private lateinit var reportname: EditText
    private lateinit var reportnumber: EditText
    private lateinit var reporttype: EditText
    private lateinit var reportprogres: ProgressBar
    private lateinit var reportbtun: Button
    private lateinit var reFirebase: DatabaseReference

    private var mFirebaseDatabaseInstances: FirebaseDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_report1, container, false)
        reportname = view.findViewById(R.id.rename)
        reportnumber = view.findViewById(R.id.renumber)
        reporttype = view.findViewById(R.id.retype)
        reportprogres = view.findViewById(R.id.reprogressBar)
        reportbtun = view.findViewById(R.id.rebtn)

        reportprogres!!.visibility = View.INVISIBLE

        reportbtun.setOnClickListener {

            if (TextUtils.isEmpty(reportname.toString())) {
                Toast.makeText(context, "Enter Username!", Toast.LENGTH_LONG).show()
            }
            if (TextUtils.isEmpty(reportnumber.toString())) {
                Toast.makeText(context, "Enter number!", Toast.LENGTH_LONG).show()
            }
            if (TextUtils.isEmpty(reporttype.toString())) {
                Toast.makeText(context, "Enter type", Toast.LENGTH_LONG).show()
            }
            mAuth = FirebaseAuth.getInstance()
            mFirebaseDatabaseInstances = FirebaseDatabase.getInstance()
            val uname = reportname.text.toString()
            val unumber = reportnumber.text.toString()
            val utype = reporttype.text.toString()

            reportprogres!!.visibility = View.VISIBLE
            reFirebase = FirebaseDatabase.getInstance().getReference("Reports")

            val userid = reFirebase.push().key!!
            val registration = reports(userid, uname, unumber, utype)
            reFirebase.child(userid).setValue(registration).addOnSuccessListener {

                Toast.makeText(context, "Data inserted successfully", Toast.LENGTH_LONG).show()
                reportprogres!!.visibility = View.INVISIBLE

                reportname.setText("")
                reportnumber.setText("")
                reporttype.setText("")
            }
        }

        // Listen for changes to the reports node and add phone numbers to the blocklist
        listenToReportsNode()

        return view
    }

    private fun listenToReportsNode() {
        val database = FirebaseDatabase.getInstance()

        // Create a reference to the reports node in the database
        val reportsRef = database.getReference("Reports")
        val blocklistRef = database.getReference("blocklist")

        // Create a reference to the blocklist node in the database


        // Listen for changes to the reports node
        reportsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
// Create a map to store the number of times each phone number appears in the reports node

                // Create a map to store the number of times each phone number appears in the reports node
                val phoneNumberCount = HashMap<String, Int>()

                // Iterate over all the reports and count the number of times each phone number appears
                for (report in snapshot.children) {
                    val phoneNumber = report.child("reportednumber").value as String
                    phoneNumberCount[phoneNumber] = (phoneNumberCount[phoneNumber] ?: 0) + 1
                }

                // Iterate over the phone numbers that have been reported more than two times and add them to the blocklist
                for ((phoneNumber, count) in phoneNumberCount) {
                    if (count > 2) {
                        blocklistRef.child("+91$phoneNumber").setValue(true)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to retrieve reports", error.toException())
            }
        })
    }
}