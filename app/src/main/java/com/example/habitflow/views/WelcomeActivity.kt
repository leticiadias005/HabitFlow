package com.example.habitflow.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.habitflow.databinding.ActivityWelcomeBinding

/**
 * Tela 1 (Views/XML + ViewBinding).
 *
 * Interação tratada: o toque em "Começar" avança o fluxo para a lista de hábitos
 * por meio de uma Intent explícita.
 */
class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonStart.setOnClickListener {
            startActivity(Intent(this, HabitListActivity::class.java))
        }
    }
}
