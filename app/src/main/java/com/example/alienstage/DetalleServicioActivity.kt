package com.example.alienstage

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.alienstage.databinding.ActivityDetalleServicioBinding

class DetalleServicioActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDetalleServicioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetalleServicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("idservicio")
        val nombre = intent.getStringExtra("nombre")
        val descipcion = intent.getStringExtra("descripcion")
        val estatus = intent.getStringExtra("estatus")
        val imagen = intent.getStringExtra("imagen")
        val resena = intent.getStringExtra("resena")
        val precio = intent.getStringExtra("precio")



        binding.tvIdServicio.text = id
        binding.tvNombre.text = nombre
        binding.tvDescripcion.text = descipcion
        binding.tvEstatus.text = estatus
        binding.tvPrecio.text=precio
        binding.tvResena.text=resena
        Glide.with(this).load(imagen).into(binding.ivServicio)
    }
}