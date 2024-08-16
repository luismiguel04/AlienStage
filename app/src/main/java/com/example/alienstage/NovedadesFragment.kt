package com.example.alienstage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.alienstage.adaptador.NovedadAdapter
import com.example.alienstage.databinding.FragmentNovedadesBinding
import org.json.JSONArray
import org.json.JSONObject


class NovedadesFragment : Fragment(R.layout.fragment_novedades) {
    private var _binding: FragmentNovedadesBinding? = null
    private val binding get() = _binding!!
    private lateinit var requestQueue: RequestQueue
    private val arrayList = ArrayList<Novedad>()
    private val displayList = ArrayList<Novedad>()
    private var userId: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNovedadesBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId = arguments?.getString("USER_ID").toString()
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
        requestQueue = Volley.newRequestQueue(requireContext())
        fetchServicios()
    }

    private fun fetchServicios() {
        val url = "https://ladetechnologies.com/consultanovedades.php"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                arrayList.clear()
                displayList.clear()
                val jsonArray = JSONArray(response)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = JSONObject(jsonArray.getString(i))
                    val tiponovedad = jsonObject.getString("tiponovedad")
                    val descripcion = jsonObject.getString("descripcion")
                    val nivel = jsonObject.getString("nivel")
                    val estatus = jsonObject.getString("estatus")


                    arrayList.add(Novedad(tiponovedad, descripcion, nivel,estatus))
                }
                displayList.addAll(arrayList)


                binding.rvservicios.layoutManager = LinearLayoutManager(requireContext())
                binding.rvservicios.adapter = NovedadAdapter(displayList) { Novedad  ->
                    onItemSelected(Novedad)
                }
            },
            { error ->
                Toast.makeText(requireContext(), "Error fetching data: ${error.message}", Toast.LENGTH_LONG).show()
                Log.e("ServiciosFragment", "Error fetching data: ${error.message}", error)
            }
        )

        requestQueue.add(stringRequest)
    }

    private fun onItemSelected(novedad: Novedad) {
        Toast.makeText(requireContext(), novedad.tiponovedad, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
