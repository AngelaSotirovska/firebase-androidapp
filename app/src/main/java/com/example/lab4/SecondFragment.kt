package com.example.lab4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab4.adapter.RecyclerViewAdapter
import com.example.lab4.model.Student
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase


class SecondFragment : Fragment() {

    var reference = FirebaseDatabase.getInstance().getReference("students")
    var studentsList : MutableList<Student> = mutableListOf()
    lateinit var adapter: RecyclerViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView : RecyclerView = view.findViewById(R.id.myRecView)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        val options: FirebaseRecyclerOptions<Student> = FirebaseRecyclerOptions.Builder<Student>()
            .setQuery(FirebaseDatabase.getInstance().getReference("students"), Student::class.java)
            .build()

        adapter = RecyclerViewAdapter(options)
        recyclerView.adapter=adapter

        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }
    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}