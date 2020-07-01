package com.ataberkertan.sportsquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.logout_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logout){
            auth.signOut()
            val intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        welcomeText.text = "Hoşgeldiniz ${auth.currentUser!!.email}, aşağıdaki resimlere tıklayarak quiz konusunu seçebilirsiniz"

    }

    fun footballClicked(view: View){
        val intent = Intent(applicationContext,QuestionsActivity::class.java)
        intent.putExtra("info","football")
        startActivity(intent)

    }
    fun basketballClicked(view:View){
        val intent = Intent(applicationContext,QuestionsActivity::class.java)
        intent.putExtra("info","basketball")
        startActivity(intent)

    }
    fun motorsportsClicked(view:View){
        val intent = Intent(applicationContext,QuestionsActivity::class.java)
        intent.putExtra("info","motorsports")
        startActivity(intent)
    }
    fun tennisClicked(view:View){
        val intent = Intent(applicationContext,QuestionsActivity::class.java)
        intent.putExtra("info","tennis")
        startActivity(intent)
    }

}
