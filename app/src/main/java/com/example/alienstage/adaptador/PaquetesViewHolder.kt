package com.example.alienstage.adaptador


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.alienstage.DetalleServicioActivity
import com.example.alienstage.R
import com.example.alienstage.databinding.ItemServicioBinding
import com.example.alienstage.paquete
import java.text.NumberFormat
import java.util.Locale

class PaquetesViewHolder(view: View): RecyclerView.ViewHolder(view){

    val binding = ItemServicioBinding.bind(view)





    fun render(ServicioModel: paquete, onClickListener: (paquete) ->Unit){
        binding.tvidServicio.text= ServicioModel.idPaquete.toString()
        binding.tvNombre.text =ServicioModel.nombre
        binding.tvDescripcion.text =ServicioModel.descripcion

        val formatoMoneda = NumberFormat.getCurrencyInstance(Locale.getDefault())
        val precioFormateado = formatoMoneda.format(ServicioModel.precio)
        binding.tvPrecio.text = precioFormateado

        binding.tvEstatus.text=ServicioModel.estatus
        fun decodeBase64ToBitmap(base64Str: String): Bitmap? {
            return try {
                val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
                BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
            } catch (e: IllegalArgumentException) {
                e.printStackTrace() // Puedes agregar un log para depuración
                null
            }
        }

        val bitmap = decodeBase64ToBitmap(ServicioModel.foto)
        if (bitmap != null) {
            binding.ivImagen.setImageBitmap(bitmap)
        } else {
            // Si la imagen no se puede decodificar, mostrar una imagen de marcador de posición
            binding.ivImagen.setImageResource(R.drawable.preview) // Reemplaza con tu imagen de marcador de posición
        }


        itemView.setOnClickListener {
            onClickListener(ServicioModel)
            val intent = Intent(itemView.context,DetalleServicioActivity::class.java).apply {
                putExtra("idservicio",ServicioModel.idPaquete.toString())
                putExtra("nombre",ServicioModel.nombre)
                putExtra("precio",ServicioModel.precio.toString())
                putExtra("estatus",ServicioModel.estatus)
                putExtra("descripcion",ServicioModel.descripcion)
                putExtra("resena",ServicioModel.resena)
                putExtra("horas",ServicioModel.horas)




            }
            itemView.context.startActivity(intent)
        }
    }
}