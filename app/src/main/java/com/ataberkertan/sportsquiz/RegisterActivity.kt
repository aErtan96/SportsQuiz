package com.ataberkertan.sportsquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {




    private lateinit var auth :FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

    }

    fun registerClicked(view: View){
        var email = emailText.text.toString()
        var password = passwordText.text.toString()
        var username = usernameText.text.toString()
        var confirmPassword = confirmPasswordText.text.toString()




            if(username != null && password == confirmPassword){
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{task ->


                    if(task.isSuccessful){
                        Toast.makeText(applicationContext,"Registered Successfully",Toast.LENGTH_LONG).show()
                        val intent = Intent(applicationContext,MainActivity::class.java)
                        intent.putExtra("Username",username)
                        startActivity(intent)
                        finish()

                    }


                }.addOnFailureListener { exception ->
                    if(exception!= null){
                        Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()
                    }
                }
            }else {
                Toast.makeText(applicationContext,"Please confirm your password",Toast.LENGTH_LONG).show()
            }




        }

}




