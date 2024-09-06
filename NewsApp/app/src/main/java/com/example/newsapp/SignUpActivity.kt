package com.example.newsapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.newsapp.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import android.view.View
import com.google.firebase.FirebaseApp


class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding
    private var selectedCountry: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth
        setupCountrySpinner()

        binding.alreadyUserTv.setOnClickListener {
            startActivity(Intent(this, LogIn::class.java))
            finish()
        }

        binding.signUpBtn.setOnClickListener {
            val email = binding.emailEt.text.toString().trim()
            val pass = binding.passwordEt.text.toString().trim()
            val conPass = binding.conPassEt.text.toString().trim()
            if (email.isBlank() || pass.isBlank() || conPass.isBlank())
                Toast.makeText(this, "Missing Field/s!", Toast.LENGTH_SHORT).show()
            else if (pass.length < 6)
                Toast.makeText(this, "Short Password!", Toast.LENGTH_SHORT).show()
            else if (pass != conPass)
                Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show()
            else {
                binding.progress.isVisible = true
                signUpUser(email, pass)
            }
        }

    }

    private fun setupCountrySpinner() {
        val countries = listOf(
            "Select Country",
            "us",
            "eg",
            "de"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, countries)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.countrySpinner.adapter = adapter

        binding.countrySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            // override fun onItemSelected(
            // parent: AdapterView<*>,
            //view: View,
            // position: Int,
            // id: Long
            //  ) {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // selectedCountry = parent.getItemAtPosition(position).toString()
                // Handle the case where the user selects the first item ("Select Country")
                val item = parent.getItemAtPosition(position)
                selectedCountry = item?.toString() ?: ""
                if (selectedCountry == "Select Country") {
                    selectedCountry = ""
                }
                else{
                    val sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("Country",selectedCountry)
                    editor.apply()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle case when nothing is selected if needed
            }
        }}

    private fun signUpUser(email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) { task ->
            if (task.isSuccessful)
                verifyEmail()
            else {
                val message = task.exception!!.message!!
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                binding.progress.isVisible = false
            }
        }
    }


    private fun verifyEmail() {
        auth.currentUser?.sendEmailVerification()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("SignUpActivity", "Email verification sent")
                Toast.makeText(this, "Check your email to verify!", Toast.LENGTH_SHORT).show()
                binding.progress.isVisible = false
            } else {
                Log.e("SignUpActivity", "Email verification failed: ${task.exception?.message}")
                Toast.makeText(this, "Email verification failed", Toast.LENGTH_SHORT).show()
                binding.progress.isVisible = false
            }
        }
    }}