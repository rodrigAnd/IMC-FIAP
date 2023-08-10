package com.example.imc

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.imc.databinding.ActivityMainBinding
import com.example.imc.extensions.format
import com.example.imc.extensions.valueDouble
import com.example.imc.utils.DecimalTextWatcher
import com.example.imc.utils.fullScreen
import com.example.imc.utils.hideKeyboard

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreen()
        setContentView(binding.root)
        hideComponents()
        setupListeners()
    }

    private fun setupListeners() {
        binding.btCalcular.setOnClickListener {
            binding.btCalcular.hideKeyboard()
            calcular()
        }

        binding.etPeso.addTextChangedListener(DecimalTextWatcher(binding.etPeso, 1))
        binding.etAltura.addTextChangedListener(DecimalTextWatcher(binding.etAltura, 2))
    }

    private fun calcular() {
        
        val peso = binding.etPeso.valueDouble()
        val altura = binding.etAltura.valueDouble()
        val imc = peso / (altura * altura)
        if (peso == 0.0 || altura == 0.0) {
            Toast.makeText(this, "Digite os valores corretamente", Toast.LENGTH_SHORT).show()
            return
        }
        when (imc) {
            in 0.0..18.5 -> configuraIMC(imc, R.drawable.masc_abaixo, R.string.magreza)
            in 18.6..24.9 -> configuraIMC(imc, R.drawable.masc_ideal, R.string.peso_normal)
            in 25.0..29.9 -> configuraIMC(imc, R.drawable.masc_sobre, R.string.sobre_peso)
            in 30.0..34.9 -> configuraIMC(imc, R.drawable.masc_obeso, R.string.obesidade_grau_i)
            in 35.0..39.9 -> configuraIMC(
                imc,
                R.drawable.masc_extremo_obeso,
                R.string.obesidade_grau_ii
            )

            else -> configuraIMC(imc, R.drawable.masc_extremo_obeso, R.string.obesidade_grau_iii)
        }
    }

    private fun configuraIMC(imc: Double, drawableId: Int, stringId: Int) {
        viewComponets()
        binding.tvIMC.text = getString(R.string.seu_imc, imc.format(2))
        binding.ivIMCStatus.setImageDrawable(
            ContextCompat.getDrawable(this, drawableId)
        )
        binding.tvIMCStatus.text = getString(stringId)
    }

    private fun viewComponets() {
        binding.tvIMC.visibility = View.VISIBLE
        binding.tvIMCStatus.visibility = View.VISIBLE
        binding.ivIMCStatus.visibility = View.VISIBLE
    }

    private fun hideComponents() {
        binding.tvIMC.visibility = View.GONE
        binding.tvIMCStatus.visibility = View.GONE
        binding.ivIMCStatus.visibility = View.GONE
    }
}
