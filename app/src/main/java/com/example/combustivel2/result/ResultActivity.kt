package com.example.combustivel2.result

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.combustivel2.R
import com.example.combustivel2.extensions.format
import com.example.combustivel2.model.CarData
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        calculate()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    private fun calculate() {


        val carData = intent.extras?.getParcelable<CarData>("CAR_DATA")

        carData?.run {
            val performanceOfMyCar = ethanolAverage / gasAverage
            val priceOfFuelIndice = ethanolPrice / gasPrice

            if (priceOfFuelIndice <= performanceOfMyCar) {
                tvSuggestion.text = getString(R.string.ethanol)
            } else {
                tvSuggestion.text = getString(R.string.gasoline)
            }
            tvEthanolAverageResult.text = (ethanolPrice / ethanolAverage).format(2)
            tvGasAverageResult.text = (gasPrice / gasAverage).format(2)

            tvFuelRatio.text = getString(
                R.string.label_fuel_ratio,
                performanceOfMyCar.format(2)
            )

        }
    }
}