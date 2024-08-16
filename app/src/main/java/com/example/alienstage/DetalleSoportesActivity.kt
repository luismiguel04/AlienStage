package com.example.alienstage

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import com.example.alienstage.databinding.ActivityDetalleServicioBinding
import com.example.alienstage.databinding.ActivityDetalleSoportesBinding

class DetalleSoportesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleSoportesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetalleSoportesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("idsoporte")
        val correo = intent.getStringExtra("correo")
        val descipcion = intent.getStringExtra("descripcion")
        val situacion = intent.getStringExtra("situacion")
        val fecha = intent.getStringExtra("fecha")
        val solucion = intent.getStringExtra("solucion")
        val evidencia = intent.getStringExtra("evidencia").toString()

        fun decodeBase64ToBitmap(base64Str: String): Bitmap? {
            return try {
                val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
                BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
                null
            }
        }

        val bitmap = decodeBase64ToBitmap(evidencia)
        binding.ivEvidencia.setImageBitmap(bitmap)







        binding.tvIdSoporte.text = id
        binding.tvCorreo.text = correo
        binding.tvDescripcion.text = descipcion
        binding.tvSituacion.text = situacion
        binding.tvFecha.text=fecha
        binding.tvsol.text=solucion

        binding.ibBack.setOnClickListener {
            onBackPressed()
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
