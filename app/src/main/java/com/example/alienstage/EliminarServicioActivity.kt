package com.example.alienstage

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.alienstage.databinding.ActivityEliminarServicioBinding

class EliminarServicioActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEliminarServicioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEliminarServicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("idservicio")

        binding.btnEliminar.setOnClickListener {
            if (id != null) {
                cancelarServicio(id)
            } else {
                Toast.makeText(this, "No se encontró su servicio", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun cancelarServicio(id: String) {


    }

}

