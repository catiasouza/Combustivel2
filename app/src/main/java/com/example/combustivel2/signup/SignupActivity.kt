package com.example.combustivel2.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.combustivel2.R
import com.example.combustivel2.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth  //1 declara primeiro a autenticacao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        mAuth = FirebaseAuth.getInstance()   //2 instancia o mauth

        id_custom_button.setOnClickListener {  //3 evento de clique

            mAuth.createUserWithEmailAndPassword(  // 4 faz a autenticacao do email e senha
                id_input_email.text.toString(),    // 5 captura o email digitado pelo usuario
                id_input_password.text.toString()   // 6 captura a senha digitada
            ).addOnCompleteListener {                //7 adiciona
                if (it.isSuccessful) {              //8 se for sucesso
                    saveInRealTimeDatabase()        // 9 salva
                } else {
                    Toast.makeText(this@SignupActivity, it.exception?.message, // 10 senao dispara a mensagem de erro
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun saveInRealTimeDatabase() {
        val user = User(id_input_name.text.toString(),  //11 guarda as variaveis em user, msm campo da classe user
            id_input_email.text.toString(),
            id_input_tel.text.toString())
        FirebaseDatabase.getInstance().getReference("Users") // 12 instancia no firebase e guarda dentro da variavel ""
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .setValue(user)
            .addOnCompleteListener {         // 13 add
                if (it.isSuccessful) {       // 14 se for sucesso
                    Toast.makeText(this, "Usuário criado com sucesso",  // 15 mostra p usuario a mensagem
                        Toast.LENGTH_SHORT).show()
                    val returnIntent = Intent()                                     // 16 cria a intent
                    returnIntent.putExtra("email", id_input_email.text.toString())  //17 e envia email para o firebase
                    setResult(RESULT_OK, returnIntent)
                    finish()

                } else {
                    Toast.makeText(this, "Erro ao criar o usuário", Toast.LENGTH_SHORT).show()
                }
            }
    }
}


