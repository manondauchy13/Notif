package fr.logkey.notif

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : AppCompatActivity() {
    private lateinit var EmailTB: EditText;
    private lateinit var PassTB:EditText;
    private lateinit var LoginB: Button;
    private lateinit var auth: FirebaseAuth
    private lateinit var uid : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Write a message to the database
        // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")

        myRef.setValue("Hello, World!")
        EmailTB = findViewById(R.id.EmailTB)
        PassTB = findViewById(R.id.PassTB)
        LoginB = findViewById(R.id.Login)

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()


        retrieveAndStoreToken()

        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->

                if (!task.isSuccessful) {
                    Log.w("???", "Fetching FCM token failed", task.exception)
                    return@OnCompleteListener
                }
                val token = task.result

                Log.d("???", "token is $token")
            })
        checkFCMBundle(intent)

        if (FirebaseAuth.getInstance().getCurrentUser()!= null) {
            startActivity(Intent(this@MainActivity,SendNotifActivity::class.java))
        }else{
            LoginB.setOnClickListener(View.OnClickListener {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(EmailTB.text.toString().trim(), PassTB.text.toString().trim()).addOnSuccessListener {
                    startActivity(Intent(this@MainActivity,SendNotifActivity::class.java))


                }
            })
        }
    }
}

private fun retrieveAndStoreToken(){
    FirebaseMessaging.getInstance().token
        .addOnCompleteListener { task ->

            if (task.isSuccessful) {

                val token = task.result
                val userId = Firebase.auth.currentUser

                FirebaseDatabase.getInstance()
                    .getReference("Tokens")
                    .child(userId.toString())
                    .setValue(token)
            }

        }
}
private fun checkFCMBundle(intent: Intent) {
    val bundle = intent.extras

    bundle?.let {
        val title = it["title"]
        val body = it["body"]
        val message = it["message"]
        val pushType = it["pushType"]

        Log.d("???", "$title")
        Log.d("???", "$body")
        Log.d("???", "$message")
        Log.d("???", "$pushType")




        }
    }
