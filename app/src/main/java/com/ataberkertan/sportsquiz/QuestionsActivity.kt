package com.ataberkertan.sportsquiz

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_questions.*

class QuestionsActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    private lateinit var db: FirebaseFirestore
    lateinit var countdownTimer : CountDownTimer

    var runnable = Runnable{ }
    var handler = Handler()



    var footballQuestions : ArrayList<String?> = ArrayList()
    var footballOptions1: ArrayList<String?> = ArrayList()
    var footballOptions2: ArrayList<String?> = ArrayList()
    var footballOptions3: ArrayList<String?> = ArrayList()
    var footballOptions4: ArrayList<String?> = ArrayList()
    var footballAnswers: ArrayList<String?> = ArrayList()


    var basketballQuestions : ArrayList<String?> = ArrayList()
    var basketballOptions1: ArrayList<String?> = ArrayList()
    var basketballOptions2: ArrayList<String?> = ArrayList()
    var basketballOptions3: ArrayList<String?> = ArrayList()
    var basketballOptions4: ArrayList<String?> = ArrayList()
    var basketballAnswers: ArrayList<String?> = ArrayList()


    var motorsportsQuestions : ArrayList<String?> = ArrayList()
    var motorsportsOptions1: ArrayList<String?> = ArrayList()
    var motorsportsOptions2: ArrayList<String?> = ArrayList()
    var motorsportsOptions3: ArrayList<String?> = ArrayList()
    var motorsportsOptions4: ArrayList<String?> = ArrayList()
    var motorsportsAnswers: ArrayList<String?> = ArrayList()

    var tennisQuestions : ArrayList<String?> = ArrayList()
    var tennisOptions1: ArrayList<String?> = ArrayList()
    var tennisOptions2: ArrayList<String?> = ArrayList()
    var tennisOptions3: ArrayList<String?> = ArrayList()
    var tennisOptions4: ArrayList<String?> = ArrayList()
    var tennisAnswers: ArrayList<String?> = ArrayList()




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
        setContentView(R.layout.activity_questions)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()




        val intentFromCategories = intent
        val info = intent.getStringExtra("info")
        if(info.equals("football")){
            getFootballQuestionsFromFireStore()
        }
        else if(info.equals("basketball")){
            getBasketballQuestionsFromFireStore()
        }
        else if(info.equals("motorsports")){
            getMotorsportsQuestionsFromFirestore()
        }
        else if(info.equals("tennis")){
            getTennisQuestionsFromFireStore()
        }



    }

    fun getFootballQuestionsFromFireStore(){


        var myCollection = db.collection("FootballQuestions")

        myCollection.addSnapshotListener{ snapshot, exception ->
            if(exception != null){
                Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()
            }else{
                if (snapshot != null){
                    if(!snapshot.isEmpty){
                        val documents = snapshot.documents

                        footballQuestions.clear()
                        footballOptions1.clear()
                        footballOptions2.clear()
                        footballOptions3.clear()
                        footballOptions4.clear()
                        footballAnswers.clear()

                        for (document in documents){

                            val question = document.get("question") as? String
                            val option1 = document.get("option1") as? String
                            val option2  = document.get("option2") as? String
                            val option3  = document.get("option3") as? String
                            val option4 = document.get("option4") as? String
                            val answer = document.get("answer") as? String

                            footballQuestions.add(question)
                            footballOptions1.add(option1)
                            footballOptions2.add(option2)
                            footballOptions3.add(option3)
                            footballOptions4.add(option4)
                            footballAnswers.add(answer)

                        }


                        var questionNumber = 0


                        questionText.text = footballQuestions[questionNumber]
                        option1Text.text = footballOptions1[questionNumber]
                        option2Text.text = footballOptions2[questionNumber]
                        option3Text.text = footballOptions3[questionNumber]
                        option4Text.text = footballOptions4[questionNumber]

                        val option1clicked = findViewById<TextView>(R.id.option1Text)
                        option1clicked.setOnClickListener {

                            if(footballOptions1[questionNumber] == footballAnswers[questionNumber]){
                                questionNumber++

                                if(questionNumber == footballQuestions.size){
                                    countdownTimer.cancel()
                                    val alert = AlertDialog.Builder(this@QuestionsActivity)
                                    alert.setTitle("Bütün Soruları Bildiniz")

                                    alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                        val intent = Intent(applicationContext,LoginActivity::class.java)
                                        startActivity(intent)
                                    }
                                    alert.show()
                                }else{
                                    questionText.text = footballQuestions[questionNumber]
                                    option1Text.text = footballOptions1[questionNumber]
                                    option2Text.text = footballOptions2[questionNumber]
                                    option3Text.text = footballOptions3[questionNumber]
                                    option4Text.text = footballOptions4[questionNumber]


                                    countdownTimer.cancel()
                                    countdownTimer.start()
                                }



                            }else {
                                countdownTimer.cancel()
                                option1Text.setBackgroundColor(Color.parseColor("#ff0000"))
                                val alert = AlertDialog.Builder(this@QuestionsActivity)
                                alert.setTitle("Yanlış Cevap")

                                alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                    val intent = Intent(applicationContext,LoginActivity::class.java)
                                    startActivity(intent)
                                }
                                alert.show()
                            }
                        }

                        val option2clicked = findViewById<TextView>(R.id.option2Text)
                        option2clicked.setOnClickListener {
                            if(footballOptions2[questionNumber] == footballAnswers[questionNumber]){
                                questionNumber++
                                if(questionNumber == footballQuestions.size){
                                    countdownTimer.cancel()
                                    val alert = AlertDialog.Builder(this@QuestionsActivity)
                                    alert.setTitle("Bütün Soruları Bildiniz")

                                    alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                        val intent = Intent(applicationContext,LoginActivity::class.java)
                                        startActivity(intent)
                                    }
                                    alert.show()
                                }else{
                                    questionText.text = footballQuestions[questionNumber]
                                    option1Text.text = footballOptions1[questionNumber]
                                    option2Text.text = footballOptions2[questionNumber]
                                    option3Text.text = footballOptions3[questionNumber]
                                    option4Text.text = footballOptions4[questionNumber]


                                    countdownTimer.cancel()
                                    countdownTimer.start()
                                }


                            }else {
                                countdownTimer.cancel()
                                option2Text.setBackgroundColor(Color.parseColor("#ff0000"))
                                val alert = AlertDialog.Builder(this@QuestionsActivity)
                                alert.setTitle("Yanlış Cevap")

                                alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                    val intent = Intent(applicationContext,LoginActivity::class.java)
                                    startActivity(intent)
                                }
                                alert.show()
                            }
                        }

                        val option3clicked = findViewById<TextView>(R.id.option3Text)
                        option3clicked.setOnClickListener {
                            if(footballOptions3[questionNumber] == footballAnswers[questionNumber]){
                                questionNumber++
                                if(questionNumber == footballQuestions.size){
                                    countdownTimer.cancel()
                                    val alert = AlertDialog.Builder(this@QuestionsActivity)
                                    alert.setTitle("Bütün Soruları Bildiniz")

                                    alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                        val intent = Intent(applicationContext,LoginActivity::class.java)
                                        startActivity(intent)
                                    }
                                    alert.show()
                                }else{
                                    questionText.text = footballQuestions[questionNumber]
                                    option1Text.text = footballOptions1[questionNumber]
                                    option2Text.text = footballOptions2[questionNumber]
                                    option3Text.text = footballOptions3[questionNumber]
                                    option4Text.text = footballOptions4[questionNumber]


                                    countdownTimer.cancel()
                                    countdownTimer.start()
                                }

                            }else {
                                countdownTimer.cancel()
                                option3Text.setBackgroundColor(Color.parseColor("#ff0000"))
                                val alert = AlertDialog.Builder(this@QuestionsActivity)
                                alert.setTitle("Yanlış Cevap")

                                alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                    val intent = Intent(applicationContext,LoginActivity::class.java)
                                    startActivity(intent)
                                }
                                alert.show()
                            }
                        }

                        val option4clicked = findViewById<TextView>(R.id.option4Text)
                        option4clicked.setOnClickListener {
                            if(footballOptions4[questionNumber] == footballAnswers[questionNumber]){
                                questionNumber++
                                if(questionNumber == footballQuestions.size){
                                    countdownTimer.cancel()
                                    val alert = AlertDialog.Builder(this@QuestionsActivity)
                                    alert.setTitle("Bütün Soruları Bildiniz")

                                    alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                        val intent = Intent(applicationContext,LoginActivity::class.java)
                                        startActivity(intent)
                                    }
                                    alert.show()
                                }else{
                                    questionText.text = footballQuestions[questionNumber]
                                    option1Text.text = footballOptions1[questionNumber]
                                    option2Text.text = footballOptions2[questionNumber]
                                    option3Text.text = footballOptions3[questionNumber]
                                    option4Text.text = footballOptions4[questionNumber]


                                    countdownTimer.cancel()
                                    countdownTimer.start()
                                }




                            }else {
                                countdownTimer.cancel()
                                option4Text.setBackgroundColor(Color.parseColor("#ff0000"))
                                val alert = AlertDialog.Builder(this@QuestionsActivity)
                                alert.setTitle("Yanlış Cevap")

                                alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                    val intent = Intent(applicationContext,LoginActivity::class.java)
                                    startActivity(intent)
                                }
                                alert.show()
                            }
                        }


                        countdownTimer = object : CountDownTimer(21000,1000){

                            override fun onFinish() {
                                handler.removeCallbacks(runnable)


                                val alert = AlertDialog.Builder(this@QuestionsActivity)
                                alert.setTitle("Süre bitti")

                                alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                    val intent = Intent(applicationContext,LoginActivity::class.java)
                                    startActivity(intent)
                                }

                                alert.show()
                            }

                            override fun onTick(millisUntilFinished: Long) {
                                timerText.text = "Kalan Süre: ${millisUntilFinished/1000}"
                            }

                        }.start()

                        runnable = object : Runnable{
                            override fun run() {

                            }

                        }
                        handler.post(runnable)

                    }

                }
            }
        }
    }

    fun getBasketballQuestionsFromFireStore(){

        db.collection("BasketballQuestions").limit(5).addSnapshotListener{snapshot,exception ->
            if(exception != null){
                Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()
            }else{
                if (snapshot != null){
                    if(!snapshot.isEmpty){


                        val documents = snapshot.documents

                        basketballQuestions.clear()
                        basketballOptions1.clear()
                        basketballOptions2.clear()
                        basketballOptions3.clear()
                        basketballOptions4.clear()
                        basketballAnswers.clear()

                        for (document in documents){

                            val question = document.get("question") as? String
                            val option1 = document.get("option1") as? String
                            val option2  = document.get("option2") as? String
                            val option3  = document.get("option3") as? String
                            val option4 = document.get("option4") as? String
                            val answer = document.get("answer") as? String

                            basketballQuestions.add(question)
                            basketballOptions1.add(option1)
                            basketballOptions2.add(option2)
                            basketballOptions3.add(option3)
                            basketballOptions4.add(option4)
                            basketballAnswers.add(answer)

                        }


                        var questionNumber = 0


                            questionText.text = basketballQuestions[questionNumber]
                            option1Text.text = basketballOptions1[questionNumber]
                            option2Text.text = basketballOptions2[questionNumber]
                            option3Text.text = basketballOptions3[questionNumber]
                            option4Text.text = basketballOptions4[questionNumber]

                            val option1clicked = findViewById<TextView>(R.id.option1Text)
                            option1clicked.setOnClickListener {

                                if(basketballOptions1[questionNumber] == basketballAnswers[questionNumber]){
                                    questionNumber++

                                    if(questionNumber == basketballQuestions.size){
                                        countdownTimer.cancel()
                                        val alert = AlertDialog.Builder(this@QuestionsActivity)
                                        alert.setTitle("Bütün Soruları Bildiniz")

                                        alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                            val intent = Intent(applicationContext,LoginActivity::class.java)
                                            startActivity(intent)
                                        }
                                        alert.show()
                                    }else{
                                        questionText.text = basketballQuestions[questionNumber]
                                        option1Text.text = basketballOptions1[questionNumber]
                                        option2Text.text = basketballOptions2[questionNumber]
                                        option3Text.text = basketballOptions3[questionNumber]
                                        option4Text.text = basketballOptions4[questionNumber]


                                        countdownTimer.cancel()
                                        countdownTimer.start()
                                    }



                                }else {
                                    countdownTimer.cancel()
                                    option1Text.setBackgroundColor(Color.parseColor("#ff0000"))
                                    val alert = AlertDialog.Builder(this@QuestionsActivity)
                                    alert.setTitle("Yanlış Cevap")

                                    alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                        val intent = Intent(applicationContext,LoginActivity::class.java)
                                        startActivity(intent)
                                    }
                                    alert.show()
                                }
                            }

                            val option2clicked = findViewById<TextView>(R.id.option2Text)
                            option2clicked.setOnClickListener {
                                if(basketballOptions2[questionNumber] == basketballAnswers[questionNumber]){
                                    questionNumber++
                                    if(questionNumber == basketballQuestions.size){
                                        countdownTimer.cancel()
                                        val alert = AlertDialog.Builder(this@QuestionsActivity)
                                        alert.setTitle("Bütün Soruları Bildiniz")

                                        alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                            val intent = Intent(applicationContext,LoginActivity::class.java)
                                            startActivity(intent)
                                        }
                                        alert.show()
                                    }else{
                                        questionText.text = basketballQuestions[questionNumber]
                                        option1Text.text = basketballOptions1[questionNumber]
                                        option2Text.text = basketballOptions2[questionNumber]
                                        option3Text.text = basketballOptions3[questionNumber]
                                        option4Text.text = basketballOptions4[questionNumber]


                                        countdownTimer.cancel()
                                        countdownTimer.start()
                                    }


                                }else {
                                    countdownTimer.cancel()
                                    option2Text.setBackgroundColor(Color.parseColor("#ff0000"))
                                    val alert = AlertDialog.Builder(this@QuestionsActivity)
                                    alert.setTitle("Yanlış Cevap")

                                    alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                        val intent = Intent(applicationContext,LoginActivity::class.java)
                                        startActivity(intent)
                                    }
                                    alert.show()
                                }
                            }

                            val option3clicked = findViewById<TextView>(R.id.option3Text)
                            option3clicked.setOnClickListener {
                                if(basketballOptions3[questionNumber] == basketballAnswers[questionNumber]){
                                    questionNumber++
                                    if(questionNumber == basketballQuestions.size){
                                        countdownTimer.cancel()
                                        val alert = AlertDialog.Builder(this@QuestionsActivity)
                                        alert.setTitle("Bütün Soruları Bildiniz")

                                        alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                            val intent = Intent(applicationContext,LoginActivity::class.java)
                                            startActivity(intent)
                                        }
                                        alert.show()
                                    }else{
                                        questionText.text = basketballQuestions[questionNumber]
                                        option1Text.text = basketballOptions1[questionNumber]
                                        option2Text.text = basketballOptions2[questionNumber]
                                        option3Text.text = basketballOptions3[questionNumber]
                                        option4Text.text = basketballOptions4[questionNumber]


                                        countdownTimer.cancel()
                                        countdownTimer.start()
                                    }

                                }else {
                                    countdownTimer.cancel()
                                    option3Text.setBackgroundColor(Color.parseColor("#ff0000"))
                                    val alert = AlertDialog.Builder(this@QuestionsActivity)
                                    alert.setTitle("Yanlış Cevap")

                                    alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                        val intent = Intent(applicationContext,LoginActivity::class.java)
                                        startActivity(intent)
                                    }
                                    alert.show()
                                }
                            }

                            val option4clicked = findViewById<TextView>(R.id.option4Text)
                            option4clicked.setOnClickListener {
                                if(basketballOptions4[questionNumber] == basketballAnswers[questionNumber]){
                                    questionNumber++
                                    if(questionNumber == basketballQuestions.size){
                                        countdownTimer.cancel()
                                        val alert = AlertDialog.Builder(this@QuestionsActivity)
                                        alert.setTitle("Bütün Soruları Bildiniz")

                                        alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                            val intent = Intent(applicationContext,LoginActivity::class.java)
                                            startActivity(intent)
                                        }
                                        alert.show()
                                    }else{
                                        questionText.text = basketballQuestions[questionNumber]
                                        option1Text.text = basketballOptions1[questionNumber]
                                        option2Text.text = basketballOptions2[questionNumber]
                                        option3Text.text = basketballOptions3[questionNumber]
                                        option4Text.text = basketballOptions4[questionNumber]


                                        countdownTimer.cancel()
                                        countdownTimer.start()
                                    }




                                }else {
                                    countdownTimer.cancel()
                                    option4Text.setBackgroundColor(Color.parseColor("#ff0000"))
                                    val alert = AlertDialog.Builder(this@QuestionsActivity)
                                    alert.setTitle("Yanlış Cevap")

                                    alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                        val intent = Intent(applicationContext,LoginActivity::class.java)
                                        startActivity(intent)
                                    }
                                    alert.show()
                                }
                            }


                            countdownTimer = object : CountDownTimer(21000,1000){

                                override fun onFinish() {
                                    handler.removeCallbacks(runnable)


                                    val alert = AlertDialog.Builder(this@QuestionsActivity)
                                    alert.setTitle("Süre bitti")

                                    alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                        val intent = Intent(applicationContext,LoginActivity::class.java)
                                        startActivity(intent)
                                    }

                                    alert.show()
                                }

                                override fun onTick(millisUntilFinished: Long) {
                                    timerText.text = "Kalan Süre: ${millisUntilFinished/1000}"
                                }

                            }.start()

                            runnable = object : Runnable{
                                override fun run() {

                                }

                            }
                            handler.post(runnable)

                    }

                }
            }
        }
    }

    fun getMotorsportsQuestionsFromFirestore(){
        db.collection("MotorsportsQuestions").addSnapshotListener{snapshot,exception ->
            if(exception != null){
                Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()
            }else{
                if (snapshot != null){
                    if(!snapshot.isEmpty){
                        val documents = snapshot.documents


                        motorsportsQuestions.clear()
                        motorsportsOptions1.clear()
                        motorsportsOptions2.clear()
                        motorsportsOptions3.clear()
                        motorsportsOptions4.clear()
                        motorsportsAnswers.clear()

                        for (document in documents){

                            val question = document.get("question") as? String
                            val option1 = document.get("option1") as? String
                            val option2  = document.get("option2") as? String
                            val option3  = document.get("option3") as? String
                            val option4 = document.get("option4") as? String
                            val answer = document.get("answer") as? String

                            motorsportsQuestions.add(question)
                            motorsportsOptions1.add(option1)
                            motorsportsOptions2.add(option2)
                            motorsportsOptions3.add(option3)
                            motorsportsOptions4.add(option4)
                            motorsportsAnswers.add(answer)

                        }


                        var questionNumber = 0


                        questionText.text = motorsportsQuestions[questionNumber]
                        option1Text.text = motorsportsOptions1[questionNumber]
                        option2Text.text = motorsportsOptions2[questionNumber]
                        option3Text.text = motorsportsOptions3[questionNumber]
                        option4Text.text = motorsportsOptions4[questionNumber]

                        val option1clicked = findViewById<TextView>(R.id.option1Text)
                        option1clicked.setOnClickListener {

                            if(motorsportsOptions1[questionNumber] == motorsportsAnswers[questionNumber]){
                                questionNumber++

                                if(questionNumber == motorsportsQuestions.size){
                                    countdownTimer.cancel()
                                    val alert = AlertDialog.Builder(this@QuestionsActivity)
                                    alert.setTitle("Bütün Soruları Bildiniz")

                                    alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                        val intent = Intent(applicationContext,LoginActivity::class.java)
                                        startActivity(intent)
                                    }
                                    alert.show()
                                }else{
                                    questionText.text = motorsportsQuestions[questionNumber]
                                    option1Text.text = motorsportsOptions1[questionNumber]
                                    option2Text.text = motorsportsOptions2[questionNumber]
                                    option3Text.text = motorsportsOptions3[questionNumber]
                                    option4Text.text = motorsportsOptions4[questionNumber]


                                    countdownTimer.cancel()
                                    countdownTimer.start()
                                }



                            }else {
                                countdownTimer.cancel()
                                option1Text.setBackgroundColor(Color.parseColor("#ff0000"))
                                val alert = AlertDialog.Builder(this@QuestionsActivity)
                                alert.setTitle("Yanlış Cevap")

                                alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                    val intent = Intent(applicationContext,LoginActivity::class.java)
                                    startActivity(intent)
                                }
                                alert.show()
                            }
                        }

                        val option2clicked = findViewById<TextView>(R.id.option2Text)
                        option2clicked.setOnClickListener {
                            if(motorsportsOptions2[questionNumber] == motorsportsAnswers[questionNumber]){
                                questionNumber++
                                if(questionNumber == motorsportsQuestions.size){
                                    countdownTimer.cancel()
                                    val alert = AlertDialog.Builder(this@QuestionsActivity)
                                    alert.setTitle("Bütün Soruları Bildiniz")

                                    alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                        val intent = Intent(applicationContext,LoginActivity::class.java)
                                        startActivity(intent)
                                    }
                                    alert.show()
                                }else{
                                    questionText.text = motorsportsQuestions[questionNumber]
                                    option1Text.text = motorsportsOptions1[questionNumber]
                                    option2Text.text = motorsportsOptions2[questionNumber]
                                    option3Text.text = motorsportsOptions3[questionNumber]
                                    option4Text.text = motorsportsOptions4[questionNumber]


                                    countdownTimer.cancel()
                                    countdownTimer.start()
                                }


                            }else {
                                countdownTimer.cancel()
                                option2Text.setBackgroundColor(Color.parseColor("#ff0000"))
                                val alert = AlertDialog.Builder(this@QuestionsActivity)
                                alert.setTitle("Yanlış Cevap")

                                alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                    val intent = Intent(applicationContext,LoginActivity::class.java)
                                    startActivity(intent)
                                }
                                alert.show()
                            }
                        }

                        val option3clicked = findViewById<TextView>(R.id.option3Text)
                        option3clicked.setOnClickListener {
                            if(motorsportsOptions3[questionNumber] == motorsportsAnswers[questionNumber]){
                                questionNumber++
                                if(questionNumber == motorsportsQuestions.size){
                                    countdownTimer.cancel()
                                    val alert = AlertDialog.Builder(this@QuestionsActivity)
                                    alert.setTitle("Bütün Soruları Bildiniz")

                                    alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                        val intent = Intent(applicationContext,LoginActivity::class.java)
                                        startActivity(intent)
                                    }
                                    alert.show()
                                }else{
                                    questionText.text = motorsportsQuestions[questionNumber]
                                    option1Text.text = motorsportsOptions1[questionNumber]
                                    option2Text.text = motorsportsOptions2[questionNumber]
                                    option3Text.text = motorsportsOptions3[questionNumber]
                                    option4Text.text = motorsportsOptions4[questionNumber]


                                    countdownTimer.cancel()
                                    countdownTimer.start()
                                }

                            }else {
                                countdownTimer.cancel()
                                option3Text.setBackgroundColor(Color.parseColor("#ff0000"))
                                val alert = AlertDialog.Builder(this@QuestionsActivity)
                                alert.setTitle("Yanlış Cevap")

                                alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                    val intent = Intent(applicationContext,LoginActivity::class.java)
                                    startActivity(intent)
                                }
                                alert.show()
                            }
                        }

                        val option4clicked = findViewById<TextView>(R.id.option4Text)
                        option4clicked.setOnClickListener {
                            if(motorsportsOptions4[questionNumber] == motorsportsAnswers[questionNumber]){
                                questionNumber++
                                if(questionNumber == motorsportsQuestions.size){
                                    countdownTimer.cancel()
                                    val alert = AlertDialog.Builder(this@QuestionsActivity)
                                    alert.setTitle("Bütün Soruları Bildiniz")

                                    alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                        val intent = Intent(applicationContext,LoginActivity::class.java)
                                        startActivity(intent)
                                    }
                                    alert.show()
                                }else{
                                    questionText.text = motorsportsQuestions[questionNumber]
                                    option1Text.text = motorsportsOptions1[questionNumber]
                                    option2Text.text = motorsportsOptions2[questionNumber]
                                    option3Text.text = motorsportsOptions3[questionNumber]
                                    option4Text.text = motorsportsOptions4[questionNumber]


                                    countdownTimer.cancel()
                                    countdownTimer.start()
                                }




                            }else {
                                countdownTimer.cancel()
                                option4Text.setBackgroundColor(Color.parseColor("#ff0000"))
                                val alert = AlertDialog.Builder(this@QuestionsActivity)
                                alert.setTitle("Yanlış Cevap")

                                alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                    val intent = Intent(applicationContext,LoginActivity::class.java)
                                    startActivity(intent)
                                }
                                alert.show()
                            }
                        }


                        countdownTimer = object : CountDownTimer(21000,1000){

                            override fun onFinish() {
                                handler.removeCallbacks(runnable)


                                val alert = AlertDialog.Builder(this@QuestionsActivity)
                                alert.setTitle("Süre bitti")

                                alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                    val intent = Intent(applicationContext,LoginActivity::class.java)
                                    startActivity(intent)
                                }

                                alert.show()
                            }

                            override fun onTick(millisUntilFinished: Long) {
                                timerText.text = "Kalan Süre: ${millisUntilFinished/1000}"
                            }

                        }.start()

                        runnable = object : Runnable{
                            override fun run() {

                            }

                        }
                        handler.post(runnable)

                    }

                }
            }
        }
    }

    fun getTennisQuestionsFromFireStore(){
        db.collection("TennisQuestions").addSnapshotListener{snapshot,exception ->
            if(exception != null){
                Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()
            }else{
                if (snapshot != null){
                    if(!snapshot.isEmpty){
                        val documents = snapshot.documents

                        tennisQuestions.clear()
                        tennisOptions1.clear()
                        tennisOptions2.clear()
                        tennisOptions3.clear()
                        tennisOptions3.clear()
                        tennisOptions4.clear()

                        for (document in documents){

                            val question = document.get("question") as? String
                            val option1 = document.get("option1") as? String
                            val option2  = document.get("option2") as? String
                            val option3  = document.get("option3") as? String
                            val option4 = document.get("option4") as? String
                            val answer = document.get("answer") as? String

                            tennisQuestions.add(question)
                            tennisOptions1.add(option1)
                            tennisOptions2.add(option2)
                            tennisOptions3.add(option3)
                            tennisOptions4.add(option4)
                            tennisAnswers.add(answer)

                        }


                        var questionNumber = 0


                        questionText.text = tennisQuestions[questionNumber]
                        option1Text.text = tennisOptions1[questionNumber]
                        option2Text.text = tennisOptions2[questionNumber]
                        option3Text.text = tennisOptions3[questionNumber]
                        option4Text.text = tennisOptions4[questionNumber]

                        val option1clicked = findViewById<TextView>(R.id.option1Text)
                        option1clicked.setOnClickListener {

                            if(tennisOptions1[questionNumber] == tennisAnswers[questionNumber]){
                                questionNumber++

                                if(questionNumber == tennisQuestions.size){
                                    countdownTimer.cancel()
                                    val alert = AlertDialog.Builder(this@QuestionsActivity)
                                    alert.setTitle("Bütün Soruları Bildiniz")

                                    alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                        val intent = Intent(applicationContext,LoginActivity::class.java)
                                        startActivity(intent)
                                    }
                                    alert.show()
                                }else{
                                    questionText.text = tennisQuestions[questionNumber]
                                    option1Text.text = tennisOptions1[questionNumber]
                                    option2Text.text = tennisOptions2[questionNumber]
                                    option3Text.text = tennisOptions3[questionNumber]
                                    option4Text.text = tennisOptions4[questionNumber]


                                    countdownTimer.cancel()
                                    countdownTimer.start()
                                }



                            }else {
                                countdownTimer.cancel()
                                option1Text.setBackgroundColor(Color.parseColor("#ff0000"))
                                val alert = AlertDialog.Builder(this@QuestionsActivity)
                                alert.setTitle("Yanlış Cevap")

                                alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                    val intent = Intent(applicationContext,LoginActivity::class.java)
                                    startActivity(intent)
                                }
                                alert.show()
                            }
                        }

                        val option2clicked = findViewById<TextView>(R.id.option2Text)
                        option2clicked.setOnClickListener {
                            if(tennisOptions2[questionNumber] == tennisAnswers[questionNumber]){
                                questionNumber++
                                if(questionNumber == tennisQuestions.size){
                                    countdownTimer.cancel()
                                    val alert = AlertDialog.Builder(this@QuestionsActivity)
                                    alert.setTitle("Bütün Soruları Bildiniz")

                                    alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                        val intent = Intent(applicationContext,LoginActivity::class.java)
                                        startActivity(intent)
                                    }
                                    alert.show()
                                }else{
                                    questionText.text = tennisQuestions[questionNumber]
                                    option1Text.text = tennisOptions1[questionNumber]
                                    option2Text.text = tennisOptions2[questionNumber]
                                    option3Text.text = tennisOptions3[questionNumber]
                                    option4Text.text = tennisOptions4[questionNumber]


                                    countdownTimer.cancel()
                                    countdownTimer.start()
                                }


                            }else {
                                countdownTimer.cancel()
                                option2Text.setBackgroundColor(Color.parseColor("#ff0000"))
                                val alert = AlertDialog.Builder(this@QuestionsActivity)
                                alert.setTitle("Yanlış Cevap")

                                alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                    val intent = Intent(applicationContext,LoginActivity::class.java)
                                    startActivity(intent)
                                }
                                alert.show()
                            }
                        }

                        val option3clicked = findViewById<TextView>(R.id.option3Text)
                        option3clicked.setOnClickListener {
                            if(tennisOptions3[questionNumber] == tennisAnswers[questionNumber]){
                                questionNumber++
                                if(questionNumber == tennisQuestions.size){
                                    countdownTimer.cancel()
                                    val alert = AlertDialog.Builder(this@QuestionsActivity)
                                    alert.setTitle("Bütün Soruları Bildiniz")

                                    alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                        val intent = Intent(applicationContext,LoginActivity::class.java)
                                        startActivity(intent)
                                    }
                                    alert.show()
                                }else{
                                    questionText.text = tennisQuestions[questionNumber]
                                    option1Text.text = tennisOptions1[questionNumber]
                                    option2Text.text = tennisOptions2[questionNumber]
                                    option3Text.text = tennisOptions3[questionNumber]
                                    option4Text.text = tennisOptions4[questionNumber]


                                    countdownTimer.cancel()
                                    countdownTimer.start()
                                }

                            }else {
                                countdownTimer.cancel()
                                option3Text.setBackgroundColor(Color.parseColor("#ff0000"))
                                val alert = AlertDialog.Builder(this@QuestionsActivity)
                                alert.setTitle("Yanlış Cevap")

                                alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                    val intent = Intent(applicationContext,LoginActivity::class.java)
                                    startActivity(intent)
                                }
                                alert.show()
                            }
                        }

                        val option4clicked = findViewById<TextView>(R.id.option4Text)
                        option4clicked.setOnClickListener {
                            if(tennisOptions4[questionNumber] == tennisAnswers[questionNumber]){
                                questionNumber++
                                if(questionNumber == tennisQuestions.size){
                                    countdownTimer.cancel()
                                    val alert = AlertDialog.Builder(this@QuestionsActivity)
                                    alert.setTitle("Bütün Soruları Bildiniz")

                                    alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                        val intent = Intent(applicationContext,LoginActivity::class.java)
                                        startActivity(intent)
                                    }
                                    alert.show()
                                }else{
                                    questionText.text = tennisQuestions[questionNumber]
                                    option1Text.text = tennisOptions1[questionNumber]
                                    option2Text.text = tennisOptions2[questionNumber]
                                    option3Text.text = tennisOptions3[questionNumber]
                                    option4Text.text = tennisOptions4[questionNumber]


                                    countdownTimer.cancel()
                                    countdownTimer.start()
                                }




                            }else {
                                countdownTimer.cancel()
                                option4Text.setBackgroundColor(Color.parseColor("#ff0000"))
                                val alert = AlertDialog.Builder(this@QuestionsActivity)
                                alert.setTitle("Yanlış Cevap")

                                alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                    val intent = Intent(applicationContext,LoginActivity::class.java)
                                    startActivity(intent)
                                }
                                alert.show()
                            }
                        }


                        countdownTimer = object : CountDownTimer(21000,1000){

                            override fun onFinish() {
                                handler.removeCallbacks(runnable)


                                val alert = AlertDialog.Builder(this@QuestionsActivity)
                                alert.setTitle("Süre bitti")

                                alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                    val intent = Intent(applicationContext,LoginActivity::class.java)
                                    startActivity(intent)
                                }

                                alert.show()
                            }

                            override fun onTick(millisUntilFinished: Long) {
                                timerText.text = "Kalan Süre: ${millisUntilFinished/1000}"
                            }

                        }.start()

                        runnable = object : Runnable{
                            override fun run() {

                            }

                        }
                        handler.post(runnable)

                    }

                }
            }
        }
    }





}
