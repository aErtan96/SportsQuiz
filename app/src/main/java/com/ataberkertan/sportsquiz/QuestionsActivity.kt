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

    var myOption1Array: ArrayList<String> = ArrayList()
    var myOption2Array: ArrayList<String> = ArrayList()
    var myOption3Array: ArrayList<String> = ArrayList()
    var myOption4Array: ArrayList<String> = ArrayList()
    var myQuestions : ArrayList<String> = ArrayList()
    var myAnswerArray : ArrayList<String> = ArrayList()


    var basketballQuestions : ArrayList<String?> = ArrayList()
    var basketballOptions1: ArrayList<String?> = ArrayList()
    var basketballOptions2: ArrayList<String?> = ArrayList()
    var basketballOptions3: ArrayList<String?> = ArrayList()
    var basketballOptions4: ArrayList<String?> = ArrayList()
    var basketballAnswers: ArrayList<String?> = ArrayList()





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
        /*myCollection.get().addOnSuccessListener{documentSnapshots ->
            val lastVisible = documentSnapshots.documents[documentSnapshots.size() - 1]
            val next = db.collection("FootballQuestions")
                .orderBy("answer")
                .startAfter(lastVisible)
                .limit(1)
            }

         */


        /*
        myCollection.get().addOnSuccessListener(OnSuccessListener<QuerySnapshot>(){
              fun onSuccess ( documentSnapshots : QuerySnapshot){
                  var lastVisible = documentSnapshots.documents[documentSnapshots.size() -1]

                  var next : Query = db.collection("FootballQuestions").orderBy("answer").startAfter(lastVisible)
              }
            })

         */

        myCollection.addSnapshotListener{ snapshot, exception ->
            if(exception != null){
                Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()
            }else{
                if (snapshot != null){
                    if(!snapshot.isEmpty){
                        val documents = snapshot.documents

                        //println(idOfDocument)
                        for (document in documents){
                            val question = document.get("question") as String
                            val option1 = document.get("option1") as String
                            val option2 = document.get("option2") as String
                            val option3 = document.get("option3") as String
                            val option4 = document.get("option4") as String
                            val answer = document.get("answer") as String

                            myQuestions.add(question)
                            myOption1Array.add(option1)
                            myOption2Array.add(option2)
                            myOption3Array.add(option3)
                            myOption4Array.add(option4)
                            myAnswerArray.add(answer)


                                questionText.text = question
                                option1Text.text = option1
                                option2Text.text = option2
                                option3Text.text = option3
                                option4Text.text = option4




                            /*
                            questionText.text = myQuestions[0]
                            option1Text.text = myOption1Array[0]
                            option2Text.text = myOption2Array[0]
                            option3Text.text = myOption3Array[0]
                            option4Text.text = myOption4Array[0]

                             */


                                val option1clicked = findViewById<TextView>(R.id.option1Text)
                                option1clicked.setOnClickListener {
                                    if(option1 == answer){

                                        option1Text.setBackgroundColor(Color.parseColor("#4dff00"))

                                            for(q in myQuestions){
                                                questionText.text = q
                                            }
                                            for (o1 in myOption1Array){
                                                option1Text.text = o1
                                            }

                                            for (o2 in myOption2Array){
                                                option2Text.text = o2
                                            }
                                            for (o3 in myOption3Array){
                                                option3Text.text = o3
                                            }
                                            for (o4 in myOption4Array){
                                                option4Text.text = o4
                                            }


                                    }else {
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
                                    if(option2 == answer){

                                        option2Text.setBackgroundColor(Color.parseColor("#4dff00"))

                                            for(q in myQuestions){
                                                questionText.text = q
                                            }
                                            for (o1 in myOption1Array){
                                                option1Text.text = o1
                                            }

                                            for (o2 in myOption2Array){
                                                option2Text.text = o2
                                            }
                                            for (o3 in myOption3Array){
                                                option3Text.text = o3
                                            }
                                            for (o4 in myOption4Array){
                                                option4Text.text = o4
                                            }




                                        }

                                    else {
                                        option2Text.setBackgroundColor(Color.parseColor("#ff0000"))
                                        val alert = AlertDialog.Builder(this@QuestionsActivity)
                                        alert.setTitle("Yanlış Cevap")

                                        alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                            val intent = Intent(applicationContext,LoginActivity::class.java)
                                            startActivity(intent)
                                        }

                                        alert.show()

                                    } }
                                val option3clicked = findViewById<TextView>(R.id.option3Text)
                                option3clicked.setOnClickListener {
                                    if(option3 == answer){

                                        option3Text.setBackgroundColor(Color.parseColor("#4dff00"))

                                            for(q in myQuestions){
                                                questionText.text = q
                                            }
                                            for (o1 in myOption1Array){
                                                option1Text.text = o1
                                            }

                                            for (o2 in myOption2Array){
                                                option2Text.text = o2
                                            }
                                            for (o3 in myOption3Array){
                                                option3Text.text = o3
                                            }
                                            for (o4 in myOption4Array){
                                                option4Text.text = o4
                                            }

                                    }else {
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
                                    if(option4 == answer){
                                        option4Text.setBackgroundColor(Color.parseColor("#4dff00"))

                                        /*
                                        questionText.text = myQuestions[0]
                                        option1Text.text = myOption1Array[0]
                                        option2Text.text = myOption2Array[0]
                                        option3Text.text = myOption3Array[0]
                                        option4Text.text = myOption4Array[0]

                                         */

                                            for(q in myQuestions){
                                                questionText.text = q
                                            }
                                            for (o1 in myOption1Array){
                                                option1Text.text = o1
                                            }

                                            for (o2 in myOption2Array){
                                                option2Text.text = o2
                                            }
                                            for (o3 in myOption3Array){
                                                option3Text.text = o3
                                            }
                                            for (o4 in myOption4Array){
                                                option4Text.text = o4
                                            }



                                    }else {
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




                            /*
                            questionText.text = question
                            option1Text.text = option1
                            option2Text.text = option2
                            option3Text.text = option3
                            option4Text.text = option4

                            val option1clicked = findViewById<TextView>(R.id.option1Text)
                            option1clicked.setOnClickListener {
                                if(option1 == answer){

                                    option1Text.setBackgroundColor(Color.parseColor("#4dff00"))



                                    var myQuestionNumber = 0
                                    while(myQuestionNumber<10){
                                        for(q in myQuestions){
                                            questionText.text = q
                                        }
                                        for (o1 in myOption1Array){
                                            option1Text.text = o1
                                        }

                                        for (o2 in myOption2Array){
                                            option2Text.text = o2
                                        }
                                        for (o3 in myOption3Array){
                                            option3Text.text = o3
                                        }
                                        for (o4 in myOption4Array){
                                            option4Text.text = o4
                                        }
                                        myQuestionNumber = myQuestionNumber +1



                                    }




                                    /*
                                    myCollection.get().addOnSuccessListener{documentSnapshots ->
                                        val lastVisible = documentSnapshots.documents[documentSnapshots.size() - 1]
                                        val next = db.collection("FootballQuestions")
                                            .orderBy("answer")
                                            .startAfter(lastVisible)
                                            .limit(1)
                                    }

                                     */



                                }else {
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

                             */

                            /*
                            val option2clicked = findViewById<TextView>(R.id.option2Text)
                            option2clicked.setOnClickListener {
                                if(option2 == answer){

                                    option2Text.setBackgroundColor(Color.parseColor("#4dff00"))
                                    var myQuestionNumber = 0
                                    while(myQuestionNumber<10){
                                        for(q in myQuestions){
                                            questionText.text = q
                                        }
                                        for (o1 in myOption1Array){
                                            option1Text.text = o1
                                        }

                                        for (o2 in myOption2Array){
                                            option2Text.text = o2
                                        }
                                        for (o3 in myOption3Array){
                                            option3Text.text = o3
                                        }
                                        for (o4 in myOption4Array){
                                            option4Text.text = o4
                                        }
                                        myQuestionNumber = myQuestionNumber +1



                                    }

                             */





                                 /*
                                myCollection.get().addOnSuccessListener{documentSnapshots ->
                                    val lastVisible = documentSnapshots.documents[documentSnapshots.size() - 1]
                                    val next = db.collection("FootballQuestions")
                                        .orderBy("answer")
                                        .startAfter(lastVisible)
                                        .limit(1)
                                }

                                  */


                                /*
                            }else {
                                    option2Text.setBackgroundColor(Color.parseColor("#ff0000"))
                                    val alert = AlertDialog.Builder(this@QuestionsActivity)
                                    alert.setTitle("Yanlış Cevap")

                                    alert.setNeutralButton("Menüye Dön"){dialog, which ->
                                        val intent = Intent(applicationContext,LoginActivity::class.java)
                                        startActivity(intent)
                                    }

                                    alert.show()

                            } }

                                 */

                            /*

                            val option3clicked = findViewById<TextView>(R.id.option3Text)
                            option3clicked.setOnClickListener {
                                if(option3 == answer){

                                    option3Text.setBackgroundColor(Color.parseColor("#4dff00"))
                                    var myQuestionNumber = 0
                                    while(myQuestionNumber<10){
                                        for(q in myQuestions){
                                            questionText.text = q
                                        }
                                        for (o1 in myOption1Array){
                                            option1Text.text = o1
                                        }

                                        for (o2 in myOption2Array){
                                            option2Text.text = o2
                                        }
                                        for (o3 in myOption3Array){
                                            option3Text.text = o3
                                        }
                                        for (o4 in myOption4Array){
                                            option4Text.text = o4
                                        }
                                        myQuestionNumber = myQuestionNumber +1



                                    }
                                    /*
                                    myCollection.get().addOnSuccessListener{documentSnapshots ->
                                        val lastVisible = documentSnapshots.documents[documentSnapshots.size() - 1]
                                        val next = db.collection("FootballQuestions")
                                            .orderBy("answer")
                                            .startAfter(lastVisible)
                                            .limit(1)
                                    }

                                     */



                                }else {
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

                             */

                            /*
                            val option4clicked = findViewById<TextView>(R.id.option4Text)
                            option4clicked.setOnClickListener {
                                if(option4 == answer){

                                    option4Text.setBackgroundColor(Color.parseColor("#4dff00"))
                                    var myQuestionNumber = 0
                                    while(myQuestionNumber<10){
                                        for(q in myQuestions){
                                            questionText.text = q
                                        }
                                        for (o1 in myOption1Array){
                                            option1Text.text = o1
                                        }

                                        for (o2 in myOption2Array){
                                            option2Text.text = o2
                                        }
                                        for (o3 in myOption3Array){
                                            option3Text.text = o3
                                        }
                                        for (o4 in myOption4Array){
                                            option4Text.text = o4
                                        }
                                        myQuestionNumber = myQuestionNumber +1



                                    }

                                    /*
                                    myCollection.get().addOnSuccessListener{documentSnapshots ->
                                        val lastVisible = documentSnapshots.documents[documentSnapshots.size() - 1]
                                        val next = db.collection("FootballQuestions")
                                            .orderBy("answer")
                                            .startAfter(lastVisible)
                                            .limit(1)
                                    }

                                     */

                                }else {
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

                             */


                            object : CountDownTimer(21000,1000){
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

                            //println(question)


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
                                        questionText.text = basketballQuestions[questionNumber+1]
                                        option1Text.text = basketballOptions1[questionNumber+1]
                                        option2Text.text = basketballOptions2[questionNumber+1]
                                        option3Text.text = basketballOptions3[questionNumber+1]
                                        option4Text.text = basketballOptions4[questionNumber+1]


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
                                        questionText.text = basketballQuestions[questionNumber+1]
                                        option1Text.text = basketballOptions1[questionNumber+1]
                                        option2Text.text = basketballOptions2[questionNumber+1]
                                        option3Text.text = basketballOptions3[questionNumber+1]
                                        option4Text.text = basketballOptions4[questionNumber+1]


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
                                        questionText.text = basketballQuestions[questionNumber+1]
                                        option1Text.text = basketballOptions1[questionNumber+1]
                                        option2Text.text = basketballOptions2[questionNumber+1]
                                        option3Text.text = basketballOptions3[questionNumber+1]
                                        option4Text.text = basketballOptions4[questionNumber+1]


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
                                        questionText.text = basketballQuestions[questionNumber+1]
                                        option1Text.text = basketballOptions1[questionNumber+1]
                                        option2Text.text = basketballOptions2[questionNumber+1]
                                        option3Text.text = basketballOptions3[questionNumber+1]
                                        option4Text.text = basketballOptions4[questionNumber+1]


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


                        println(basketballQuestions[0])
                        println(basketballQuestions[1])
                        println(basketballQuestions[2])
                        println(basketballQuestions[3])
                        println(basketballQuestions[4])
                        println(basketballAnswers[0])
                        println(basketballAnswers[1])
                        println(basketballAnswers[2])
                        println(basketballAnswers[3])
                        println(basketballAnswers[4])

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
                        for (document in documents){
                            val question = document.get("question") as? String
                            val option1 = document.get("option1") as? String
                            val option2  = document.get("option2") as? String
                            val option3  = document.get("option3") as? String
                            val option4 = document.get("option4") as? String
                            val answer = document.get("answer") as? String

                            println(question)

                            questionText.text = question
                            option1Text.text = option1
                            option2Text.text = option2
                            option3Text.text = option3
                            option4Text.text = option4

                            object : CountDownTimer(20000,1000){
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

    fun getTennisQuestionsFromFireStore(){
        db.collection("TennisQuestions").addSnapshotListener{snapshot,exception ->
            if(exception != null){
                Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()
            }else{
                if (snapshot != null){
                    if(!snapshot.isEmpty){
                        val documents = snapshot.documents
                        for (document in documents){
                            val question = document.get("question") as? String
                            val option1 = document.get("option1") as? String
                            val option2  = document.get("option2") as? String
                            val option3  = document.get("option3") as? String
                            val option4 = document.get("option4") as? String
                            val answer = document.get("answer") as? String

                            println(option1)
                            println(option2)
                            println(option3)
                            println(option4)

                            questionText.text = question
                            option1Text.text = option1
                            option2Text.text = option2
                            option3Text.text = option3
                            option4Text.text = option4

                            object : CountDownTimer(20000,1000){
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

    /*
    fun updateBasketballQuestions(){

        db.collection("BasketballQuestions").limit(5).addSnapshotListener{snapshot,exception ->
            if(exception != null){
                Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()
            }else{
                if (snapshot != null){
                    if(!snapshot.isEmpty){
                        val documents = snapshot.documents

                        for (document in documents){
                            val question = document.get("question") as String
                            val option1 = document.get("option1") as String
                            val option2  = document.get("option2") as String
                            val option3  = document.get("option3") as String
                            val option4 = document.get("option4") as String
                            val answer = document.get("answer") as String

                            questionText.text = question
                            option1Text.text = option1
                            option2Text.text = option2
                            option3Text.text = option3
                            option4Text.text = option4



                            val option1clicked = findViewById<TextView>(R.id.option1Text)
                            option1clicked.setOnClickListener {
                                if(option1 == answer){

                                    option1Text.setBackgroundColor(Color.parseColor("#4dff00"))
                                    updateBasketballQuestions()


                                }else {
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
                                if(option2 == answer){

                                    option2Text.setBackgroundColor(Color.parseColor("#4dff00"))
                                    updateBasketballQuestions()



                                }else {
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
                                if(option3 == answer){

                                    option3Text.setBackgroundColor(Color.parseColor("#4dff00"))
                                    updateBasketballQuestions()


                                }else {
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
                                if(option4 == answer){

                                    option4Text.setBackgroundColor(Color.parseColor("#4dff00"))
                                    updateBasketballQuestions()


                                }else {
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

                            updateBasketballQuestions()
                        }


                    }
                }
            }
        }
    }

     */
}
