package com.example.alienstage

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.alienstage.databinding.ActivityDetalleServicioBinding
import java.text.NumberFormat
import java.util.Locale

class DetalleServicioActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDetalleServicioBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetalleServicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nombreusuario = GlobalData.nombreusuario



        val id = intent.getStringExtra("idservicio")
        val nombre = intent.getStringExtra("nombre")
        val horas = intent.getStringExtra("horas")
        val descipcion = intent.getStringExtra("descripcion")
        val estatus = intent.getStringExtra("estatus")
        val resena = intent.getStringExtra("resena")
        val precioString = intent.getStringExtra("precio")

        val precio = precioString?.toDouble() ?: 0.0
        val formatoMoneda = NumberFormat.getCurrencyInstance(Locale.getDefault())
        val precioFormateado = formatoMoneda.format(precio)
        binding.tvPrecio.text = precioFormateado





        binding.tvNombre.text = nombre
        binding.tvHoras.text = horas
        binding.tvDescripcion.text = descipcion
        binding.tvEstatus.text =estatus
        binding.tvResena.text=resena


        binding.ibBack.setOnClickListener {
            onBackPressed()
        }

        binding.floatingActionButton2.setOnClickListener{
            val intent = Intent(this@DetalleServicioActivity, ListaResenasActivity::class.java)
            intent.putExtra("idPaquete", id)
            startActivity(intent)

        }

        binding.btnContratar.setOnClickListener{
            val intent = Intent(this@DetalleServicioActivity, FormularioServicioActivity::class.java)
            intent.putExtra("idPaquete", id)
            intent.putExtra("nombrep", nombre)
            intent.putExtra("descripcion",descipcion)
            intent.putExtra("cantidad",precioString)
            intent.putExtra("horas",horas)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        // Check if there is a fragment in the back stack
        val fragmentManager: FragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}