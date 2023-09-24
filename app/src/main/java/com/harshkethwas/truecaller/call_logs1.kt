package com.harshkethwas.truecaller

import android.database.Cursor
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView

class call_logs1 : Fragment() {


    private lateinit var cursor: Cursor
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_call_logs1, container, false)

        // Add a ListView widget to the layout
        val listView = view.findViewById<ListView>(R.id.listView)

        // Set the adapter for the ListView

        listView.adapter = adapter

        // Set an on-item-click listener on the ListView
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            // Get the phone number of the selected call
            val number = adapter.getItem(position)

            // Do something with the phone number
            Log.i("CallLogActivity", "Number: $number")
        }

        return view
    }




}
