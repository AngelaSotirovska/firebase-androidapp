package com.example.lab4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.lab4.model.Student
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FirstFragment : Fragment() {
    val mAuth = FirebaseAuth.getInstance()
    var database = FirebaseDatabase.getInstance()
    var studentsReference = database.getReference("students")

    private lateinit var index: EditText
    private lateinit var name: EditText
    private lateinit var surname: EditText
    private lateinit var number: EditText
    private lateinit var address: EditText

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        index = view.findViewById(R.id.forIndex)
        name = view.findViewById(R.id.forName)
        surname = view.findViewById(R.id.forSurname)
        number = view.findViewById(R.id.forNumber)
        address = view.findViewById(R.id.forAddress)

        val addButton: Button = view.findViewById(R.id.addButton)

        addButton.setOnClickListener {
            val indexValue: String = index.text.toString()
            val nameValue: String = name.text.toString()
            val surnameValue: String = surname.text.toString()
            val numberValue: String = number.text.toString()
            val addressValue: String = address.text.toString()

            if(indexValue.isNullOrEmpty() || nameValue.isNullOrEmpty() || surnameValue.isNullOrEmpty() || numberValue.isNullOrEmpty() || addressValue.isNullOrEmpty()){
                Toast.makeText(activity, "Please fill all the information!", Toast.LENGTH_LONG).show()
            }
            else{
                uploadData(indexValue, nameValue, surnameValue, numberValue, addressValue)
            }
        }

    }

    private fun uploadData(
        indexValue: String,
        nameValue: String,
        surnameValue: String,
        numberValue: String,
        addressValue: String
    ) {
        var currentStudent  = Student(mAuth.uid!!, indexValue, nameValue, surnameValue, numberValue, addressValue)

        studentsReference.push().setValue(currentStudent)
            .addOnCompleteListener(OnCompleteListener<Void?>{
                task ->
                if(task.isSuccessful){
                    Toast.makeText(activity, "Success adding", Toast.LENGTH_LONG).show()
                    index.setText("")
                    name.setText("")
                    surname.setText("")
                    number.setText("")
                    address.setText("")
                }
                else{
                    Toast.makeText(activity, task.exception!!.message, Toast.LENGTH_LONG).show()
                }
            })
    }
}