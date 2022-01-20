package com.example.lab4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.log

class LoginActivity : AppCompatActivity() {

    private val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val email: EditText = findViewById(R.id.emailId)
        val password: EditText = findViewById(R.id.passId)

        val loginButton: Button = findViewById(R.id.forLoginButton)
        val registerButton: Button = findViewById(R.id.forRegisterButton)


        loginButton.setOnClickListener{
            val emailValue: String = email.text.toString()
            val passwordValue: String = password.text.toString()
            if(emailValue.isNullOrEmpty() || passwordValue.isNullOrEmpty()){
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
            }
            else{
                login(emailValue, passwordValue)
            }
        }

        registerButton.setOnClickListener{
            val emailValue: String = email.text.toString()
            val passwordValue: String = password.text.toString()
            if(emailValue.isNullOrEmpty() || passwordValue.isNullOrEmpty()){
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
            }
            else{
                register(emailValue, passwordValue)
            }
        }
        
    }

    private fun register(emailValue: String, passwordValue: String) {
        mAuth.createUserWithEmailAndPassword(emailValue, passwordValue)
            .addOnCompleteListener(this){
                task ->
                if(task.isSuccessful){
                    Toast.makeText(this, "Registration success!", Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun login(emailValue: String, passwordValue: String) {
        mAuth.signInWithEmailAndPassword(emailValue, passwordValue)
            .addOnCompleteListener(this){
                task ->
                if(task.isSuccessful){
                    navigateOut()
                }
                else{
                    Toast.makeText(this, "Failed login!", Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()

        if(checkLoggedIn()){
            navigateOut()
        }
    }

    private fun checkLoggedIn(): Boolean {
        return mAuth.currentUser!=null
    }

    private fun navigateOut(){
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}