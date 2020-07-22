package com.byron.crypto.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.byron.crypto.R
import com.byron.crypto.model.User
import com.byron.crypto.network.Callback
import com.byron.crypto.network.FirestoreService
import com.byron.crypto.network.USERS_COLLECTION_NAME
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

const val USERNAME_KEY = "username_key"

class MainActivity : AppCompatActivity() {


    private val TAG = "MainActivity"

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    lateinit var firestoreService: FirestoreService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //obtenemos el servicio
        firestoreService = FirestoreService(FirebaseFirestore.getInstance())
    }


    fun onStartClicked(view: View) {
        view.isEnabled = false
        auth.signInAnonymously()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val username = username.text.toString()
                    firestoreService.findUserById(username, object: Callback<User> {
                        override fun onSuccess(result: User?) {
                            if(result == null){
                                val user = User()
                                user.userName = username
                                saveUserAndStartMainActivity(user,view)
                            }
                            else{
                                startMainActivity(username)
                            }
                        }
                        override fun onFailed(exception: Exception) {
                            showErrorMessage(view)
                        }
                    })
                } else {
                    showErrorMessage(view)
                    view.isEnabled = true
                }
            }

    }

    private fun saveUserAndStartMainActivity(user: User, view: View) {
        firestoreService.setDocument(user, USERS_COLLECTION_NAME,user.userName, object: Callback<Void>{
            override fun onSuccess(result: Void?) {
                startMainActivity(user.userName)
                Toast.makeText(this@MainActivity,"Ingresó", Toast.LENGTH_LONG).show()
            }

            override fun onFailed(exception: Exception) {
                showErrorMessage(view)
                Log.e(TAG,"error" , exception)
                view.isEnabled = true
            }

        })

    }

    private fun showErrorMessage(view: View) {
        Snackbar.make(view,"error_while_connecting_to_the_server", Snackbar.LENGTH_LONG)
            .setAction("Info", null).show()
    }

    private fun startMainActivity(username: String) {
        val intent = Intent(this, TraderActivity::class.java)
        intent.putExtra(USERNAME_KEY, username)
        startActivity(intent)
        finish()
    }

}
