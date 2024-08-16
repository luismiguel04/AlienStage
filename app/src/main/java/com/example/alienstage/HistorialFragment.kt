package com.example.alienstage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.alienstage.adaptador.PaquetesAdapter
import com.example.alienstage.adaptador.ServiciosAdapter
import com.example.alienstage.databinding.FragmentHistorialBinding
import com.example.alienstage.databinding.FragmentServiciosBinding
import org.json.JSONArray
import org.json.JSONObject

class HistorialFragment : Fragment(R.layout.fragment_historial) {

    private var _binding: FragmentHistorialBinding? = null
    private val binding get() = _binding!!
    private lateinit var requestQueue: RequestQueue
    private var userId: String = ""
    private val arrayList = ArrayList<Pago>()
    private val displayList = ArrayList<Pago>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistorialBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Recupera el ID del Bundle
        userId = arguments?.getString("USER_ID").toString()
        requestQueue = Volley.newRequestQueue(requireContext())
        var Id = userId.toInt()
        fetchServicios(Id)


        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        toolbar.inflateMenu(R.menu.menu_lateral)  // Infla el menú en el Toolbar

        // Manejar las opciones del menú
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuReseñas -> {
                    val intent =
                        Intent(requireContext(), ResenaActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.menuStatus -> {
                    val intent =
                        Intent(requireContext(), EstatusActivity::class.java).apply {
                            putExtra("USER_ID", userId) // Pasa el userId al Intent
                        }
                    startActivity(intent)
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

                R.id.menuCerrarSecion -> {
                    val intent =
                        Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }
    }

    private fun fetchServicios(Id: Int) {
        val url = "https://ladetechnologies.com/consultapagos.php?idweb=+$Id"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                arrayList.clear()
                displayList.clear()
                val jsonArray = JSONArray(response)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = JSONObject(jsonArray.getString(i))
                    val idPago = jsonObject.getInt("idPago")
                    val tipo = jsonObject.getString("tipo")
                    val referencia = jsonObject.getString("referencia")
                    val ubicacion = jsonObject.getString("ubicacion")
                    val fecha = jsonObject.getString("fecha")
                    val hora = jsonObject.getString("hora")
                    val duracion = jsonObject.getInt("duracion")
                    val estatus = jsonObject.getString("estatus")
                    val cantidad = jsonObject.getInt("cantidad")
                    val idRuta = jsonObject.getInt("idRuta")
                    val nombreru = jsonObject.getString("nombreru")
                    val idServicio = jsonObject.getInt("idServicio")
                    val nombreser = jsonObject.getString("nombreser")
                    val idPaquete = jsonObject.getInt("idPaquete")
                    val nombrep = jsonObject.getString("nombrep")
                    val idWeb = jsonObject.getInt("idWeb")
                    val nombre = jsonObject.getString("nombre")
                    val apellido = jsonObject.getString("apellido")
                    val correo = jsonObject.getString("correo")



                    arrayList.add(
                        Pago(
                            idPago,
                            tipo,
                            referencia,
                            ubicacion,
                            fecha,
                            hora,
                            duracion,
                            estatus,
                            cantidad,
                            idRuta,
                            nombreru,
                            idServicio,
                            nombreser,
                            idPaquete,
                            nombrep,
                            idWeb,
                            nombre,
                            apellido,
                            correo
                        )
                    )

                }
                displayList.addAll(arrayList)


                binding.rvservicios.layoutManager = LinearLayoutManager(requireContext())
                binding.rvservicios.adapter = ServiciosAdapter(displayList) { Pago ->
                    onItemSelected(Pago)
                }
            },
            { error ->
                Toast.makeText(
                    requireContext(),
                    "Error fetching data: ${error.message}",
                    Toast.LENGTH_LONG
                ).show()
                Log.e("ServiciosFragment", "Error fetching data: ${error.message}", error)
            }
        )

        requestQueue.add(stringRequest)
    }

    private fun onItemSelected(pago: Pago) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
