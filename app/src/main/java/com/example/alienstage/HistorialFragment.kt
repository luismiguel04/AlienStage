package com.example.alienstage

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

class HistorialFragment : Fragment(R.layout.fragment_historial) {

    private var userId: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Recupera el ID del Bundle
        userId = arguments?.getString("USER_ID").toString()
        if (userId.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Error: No se recibió el ID de usuario.", Toast.LENGTH_LONG).show()

        }else{
            Toast.makeText(requireContext(), userId, Toast.LENGTH_LONG).show()
        }

        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        toolbar.inflateMenu(R.menu.menu_lateral)  // Infla el menú en el Toolbar

        // Manejar las opciones del menú
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuReseñas -> {
                    Toast.makeText(requireContext(), "Reseñas seleccionado", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.menuStatus -> {
                    Toast.makeText(requireContext(), "Status seleccionado", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.menuPromociones -> {
                    Toast.makeText(requireContext(), "Promociones seleccionado", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.menuListaSoporte -> {
                    val intent =
                        Intent(requireContext(), ListaSoporteActivity::class.java).apply {
                    putExtra("USER_ID", userId) // Pasa el userId al Intent
                }
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}
