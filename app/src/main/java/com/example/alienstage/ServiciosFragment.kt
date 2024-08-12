
package com.example.alienstage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.alienstage.adaptador.PaquetesAdapter
import com.example.alienstage.adaptador.ServiciosAdapter
import com.example.alienstage.databinding.FragmentServiciosBinding
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject

class ServiciosFragment : Fragment(R.layout.fragment_servicios) {

    private var _binding: FragmentServiciosBinding? = null
    private val binding get() = _binding!!
    private lateinit var requestQueue: RequestQueue
    private val arrayList = ArrayList<paquete>()
    private val displayList = ArrayList<paquete>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServiciosBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestQueue = Volley.newRequestQueue(requireContext())
        fetchServicios()
    }

    private fun fetchServicios() {
        val url = "http://192.168.0.106/consultapaquetes.php"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                arrayList.clear()
                displayList.clear()
                val jsonArray = JSONArray(response)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = JSONObject(jsonArray.getString(i))
                    val id = jsonObject.getInt("idPaquete")
                    val nombre = jsonObject.getString("nombre")
                    val descripcion = jsonObject.getString("descripcion")
                    val precio = jsonObject.getDouble("precio")
                    val foto = jsonObject.getString("foto")
                    val resena = jsonObject.getString("reseñas")
                    val estatus = jsonObject.getString("estatus")


                    arrayList.add(paquete(id, nombre, descripcion, precio, foto, resena, estatus))
                }
                displayList.addAll(arrayList)


                binding.rvservicios.layoutManager = LinearLayoutManager(requireContext())
                binding.rvservicios.adapter = PaquetesAdapter(displayList) { paquete ->
                    onItemSelected(paquete)
                }
            },
            { error ->
                Toast.makeText(requireContext(), "Error fetching data: ${error.message}", Toast.LENGTH_LONG).show()
                Log.e("ServiciosFragment", "Error fetching data: ${error.message}", error)
            }
        )

        requestQueue.add(stringRequest)
    }

    private fun onItemSelected(paquete: paquete) {
        Toast.makeText(requireContext(), paquete.idPaquete.toString(), Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
