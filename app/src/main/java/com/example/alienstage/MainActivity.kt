package com.example.alienstage

import SoporteFragment
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        // Obtener el Intent y el id del usuario
        val intent = intent
        val userId = intent.getStringExtra("USER_ID")

        val soporteFragment = SoporteFragment().apply {
            arguments = Bundle().apply {
                putString("USER_ID", userId) // Asume que `userId` es el ID que pasas
            }
        }
        val novedadesFragment = NovedadesFragment().apply {
            arguments = Bundle().apply {
                putString("USER_ID", userId) // Asume que `userId` es el ID que pasas
            }
        }
        val serviciosFragment = ServiciosFragment().apply {
            arguments = Bundle().apply {
                putString("USER_ID", userId) // Asume que `userId` es el ID que pasas
            }
        }
        val historialFragment = HistorialFragment().apply {
            arguments = Bundle().apply {
                putString("USER_ID", userId) // Asume que `userId` es el ID que pasas
            }
        }
        val bottomNavigationView :BottomNavigationView =findViewById(R.id.bottomNavigationView)


        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuSevicios -> {
                    setCurrentFragment(serviciosFragment)
                    true

                } R.id.menuNovedades -> {
                    setCurrentFragment(novedadesFragment)
                    true

                } R.id.menuHistorial -> {
                    setCurrentFragment(historialFragment)
                    true

                } R.id.menuSoporte -> {
                    setCurrentFragment(soporteFragment)
                    true

                }
                else->false
            }
        }

    }

    private fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.containerView, fragment)
            commit()

        }

    }
}