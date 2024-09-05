package com.example.newsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.newsapp.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth

class LogIn : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    //Late initialization
    private lateinit var binding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.notUserTv.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        binding.loginBtn.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val password = binding.passwordEt.text.toString()
            if (email.isBlank() || password.isBlank())
                Toast.makeText(this, "Missing Field/s!", Toast.LENGTH_SHORT).show()
            else {
                binding.progress.isVisible = true
                login(email, password)
                //Login code
            }

        }

        binding.forgotPassTv.setOnClickListener {
            showForgotPasswordDialog()
        }

    }

    private fun login(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                binding.progress.isVisible = false
                if (auth.currentUser?.isEmailVerified == true) {
                    finish()
                } else
                    Toast.makeText(this, "Check your email to verify!", Toast.LENGTH_SHORT).show()
            } else {
                val message = task.exception!!.message
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                binding.progress.isVisible = false
            }
        }


    }


    private fun showForgotPasswordDialog() {
        val email = binding.emailEt.text.toString()
        if (email.isBlank()) {
            Toast.makeText(
                this,
                "Please enter your email to reset password",
                Toast.LENGTH_SHORT
            )
                .show()
        } else {
            sendPasswordResetEmail(email)
        }
    }

    private fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Reset email sent. Check your inbox.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val message = task.exception!!.message
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}