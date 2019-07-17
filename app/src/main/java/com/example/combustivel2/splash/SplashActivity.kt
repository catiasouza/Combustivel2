package com.example.combustivel2.splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat.startActivity
import com.example.combustivel2.R
import com.example.combustivel2.form.FormActivity
import com.example.combustivel2.login.LoginActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    //DEPOIS DE CRIADO O LAYOUT DECLARA O TEMPO PARA IR P OUTRA TELA
    private val TEMPO_AGUARDO_SPLASHSCREEN = 3500L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // 2 criar a sharedPreferences

        val preferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
        val isFirstOpen = preferences.getBoolean("open_first", true)

        if (isFirstOpen) {
            markAppAlreadyOpen(preferences) // se o aplicativo  estiver aberto pela primeira ves
            showSplash()                   // abra o splash

        } else {
            showLogin()      // faca login
        }
    }


    private fun markAppAlreadyOpen(preferences: SharedPreferences) {
        val editor = preferences.edit()
        editor.putBoolean("open_first", false)
        editor.apply()
    }

    private fun showLogin() {
        val nextScreen = Intent(this@SplashActivity, LoginActivity::class.java)
        startActivity(nextScreen)
        finish()
    }

    private fun showSplash() {
        // cria a animacao
        val anim = AnimationUtils.loadAnimation(this, R.anim.animacao_splash)
        anim.reset()
        ivLog.clearAnimation()
        ivLog.startAnimation(anim)
        // carrega a animacao
        Handler().postDelayed({
            val proximaTela= Intent(this@SplashActivity, FormActivity::class.java)
            startActivity(proximaTela)
            finish()
        }, TEMPO_AGUARDO_SPLASHSCREEN)
    }
}




