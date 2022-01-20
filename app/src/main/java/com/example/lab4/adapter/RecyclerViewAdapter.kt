package com.example.lab4.adapter

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.lab4.R
import com.example.lab4.model.Student
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.FirebaseDatabase
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.ViewHolder


class RecyclerViewAdapter(options: FirebaseRecyclerOptions<Student>) : FirebaseRecyclerAdapter<Student, RecyclerViewAdapter.ViewHolder>(options) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var index: TextView
        var firstname: TextView
        var lastname: TextView
        var editBtn: Button
        var deleteBtn: Button


        init {
            firstname = itemView.findViewById(R.id.studentName)
            lastname = itemView.findViewById(R.id.studentSurname)
            index = itemView.findViewById(R.id.studentIndex)
            editBtn = itemView.findViewById(R.id.editButton)
            deleteBtn = itemView.findViewById(R.id.deleteButton)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Student) {

        holder.firstname.setText(model.name)
        holder.lastname.setText(model.surname)
        holder.index.setText(model.index)

        holder.deleteBtn.setOnClickListener{
            var alertDialog: AlertDialog.Builder = AlertDialog.Builder(holder.itemView.context)
            alertDialog.setTitle("Are you sure?")
            alertDialog.setPositiveButton("Delete"){ dialog, which ->
                getRef(position).key?.let { it1 -> FirebaseDatabase.getInstance().getReference("students").child(it1).removeValue() }
            }
            alertDialog.setNegativeButton("Cancel"){ dialog, which ->

            }
            alertDialog.show()

        }
        holder.editBtn.setOnClickListener{
            var dialogPlus: DialogPlus = DialogPlus.newDialog(holder.itemView.context)
                    .setContentHolder(com.orhanobut.dialogplus.ViewHolder(R.layout.edit))
                    .setExpanded(true, 1300)
                    .create()
            var view: View = dialogPlus.holderView
            var editIndex: EditText = view.findViewById(R.id.editIndex)
            var editName: EditText = view.findViewById(R.id.editName)
            var editSurname: EditText = view.findViewById(R.id.editSurname)
            var editNumber: EditText = view.findViewById(R.id.editNumber)
            var editAddress: EditText = view.findViewById(R.id.editAddress)
            editIndex.setText(model.index)
            editName.setText(model.name)
            editSurname.setText(model.surname)
            editNumber.setText(model.number)
            editAddress.setText(model.address)
            dialogPlus.show()
            var confirmButton: Button = view.findViewById(R.id.confirmChangesButton)
            confirmButton.setOnClickListener {
                var map: HashMap<String, String> = hashMapOf()
                map.put("index", editIndex.text.toString())
                map.put("name", editName.text.toString())
                map.put("surname", editSurname.text.toString())
                map.put("number", editNumber.text.toString())
                map.put("address", editAddress.text.toString())
                getRef(position).key?.let { it1 ->
                    FirebaseDatabase.getInstance().getReference("students").child(it1).updateChildren(map as Map<String, Any>)
                            .addOnCompleteListener(OnCompleteListener<Void?>{
                                task ->
                                if(task.isSuccessful){
                                    dialogPlus.dismiss()
                                }
                                else{
                                    dialogPlus.dismiss()
                                    Toast.makeText(holder.itemView.context, "Error editing student!", Toast.LENGTH_LONG)
                                }
                            })
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }


}