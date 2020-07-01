package com.ataberkertan.sportsquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        //val usernameFromRegister = intent.getStringExtra( "username")


        if(currentUser!=null){
            val intent = Intent(applicationContext,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    fun loginClicked(view: View){

        var email = userEmailText.text.toString()
        var password = userPasswordText.text.toString()
        //var usernameFromRegister = usernameText.text.toString()
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {task ->
            if (task.isSuccessful){
                Toast.makeText(applicationContext, "Welcome ${auth.currentUser!!.email}",Toast.LENGTH_LONG).show()
                val intent = Intent(applicationContext,LoginActivity::class.java)

                startActivity(intent)
            }
        }.addOnFailureListener { exception ->
            if(exception != null){
                Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()
            }
        }



    }
    fun registerClicked(view: View){
        val intent = Intent(applicationContext,RegisterActivity::class.java)
        startActivity(intent)


    }

}
