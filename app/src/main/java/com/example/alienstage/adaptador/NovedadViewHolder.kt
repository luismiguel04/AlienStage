package com.example.alienstage.adaptador

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.alienstage.DetalleServicioActivity
import com.example.alienstage.Novedad
import com.example.alienstage.databinding.ItemNovedadBinding

class NovedadViewHolder (view: View): RecyclerView.ViewHolder(view){

    val binding = ItemNovedadBinding.bind(view)

    fun render(ServicioModel: Novedad, onClickListener: (Novedad) ->Unit){
        binding.tvTiponovedad.text= ServicioModel.tiponovedad
        binding.tvDescripcion.text =ServicioModel.descripcion
        binding.tvNivel.text=ServicioModel.nivel
        binding.tvEstatus.text=ServicioModel.estatus

        itemView.setOnClickListener {


            }

        }
    }
