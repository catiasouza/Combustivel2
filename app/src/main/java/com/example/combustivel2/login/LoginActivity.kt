package com.example.combustivel2.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.combustivel2.R
import com.example.combustivel2.form.FormActivity
import com.example.combustivel2.signup.SignupActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth  //18 intancia a autentiacao no login
    private val newUserRequestCode = 1        //19

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()    //19 se a autenticacao for diferente de null
        if (mAuth.currentUser != null) {
            goToHome()                           //20 chama
        }
        btLogin.setOnClickListener {     // 21 evento clique
            mAuth.signInWithEmailAndPassword(     // 22 pega email e senha e autentifica
                inputLoginEmail.text.toString(),
                inputLoginPassword.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    goToHome()
                } else {
                    Toast.makeText(this@LoginActivity, it.exception?.message,
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
        btSignup.setOnClickListener {          // 23 deu tudo certo o botao autmoaticamente faz a requisicao
            startActivityForResult(
                Intent(this, SignupActivity::class.java),
                newUserRequestCode)
        }
    }
    private fun goToHome() {
        val intent = Intent(this, FormActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == newUserRequestCode && resultCode == Activity.RESULT_OK) {
            inputLoginEmail.setText(data?.getStringExtra("email"))
        }
    }
}


