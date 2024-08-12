package com.example.alienstage

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.alienstage.adaptador.PaquetesAdapter
import com.example.alienstage.adaptador.SoportesAdapter
import com.example.alienstage.databinding.ActivityListaSoporteBinding
import org.json.JSONArray
import org.json.JSONObject

class ListaSoporteActivity : AppCompatActivity() {

    private lateinit var requestQueue: RequestQueue
    private val arrayList = ArrayList<Soporte>()
    private val displayList = ArrayList<Soporte>()
    private lateinit var binding: ActivityListaSoporteBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaSoporteBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        val intent = intent
        val userId = intent.getStringExtra("USER_ID")

        if (userId.isNullOrEmpty()) {
            Toast.makeText(this, "Error: No se recibió el ID de usuario.", Toast.LENGTH_LONG).show()
            finish() // Finaliza la actividad si no se recibe el userId
        } else {
            val Id=userId.toString().toInt()
            requestQueue = Volley.newRequestQueue(this)

            fetchSoportes(Id)
        }




    }

    private fun fetchSoportes(Id:Int) {
        val url = "http://192.168.0.106/consultasoportes.php?idweb=+$Id"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                arrayList.clear()
                displayList.clear()
                val jsonArray = JSONArray(response)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = JSONObject(jsonArray.getString(i))
                    val id = jsonObject.getInt("idSoporte")
                    val correo = jsonObject.getString("correo")
                    val descripcion = jsonObject.getString("descripcion")
                    val situacion = jsonObject.getString("situacion")
                    val fecha = jsonObject.getString("fecha")
                    val evidencia = jsonObject.getString("evidencia")


                    arrayList.add(Soporte(id, correo,situacion, descripcion,fecha,evidencia))
                }
                displayList.addAll(arrayList)


                binding.rvsoportes.layoutManager = LinearLayoutManager(this)
                binding.rvsoportes.adapter = SoportesAdapter(displayList) { Soporte ->
                    onItemSelected(Soporte)
                }
            },
            { error ->
                Toast.makeText(this, "Error fetching data: ${error.message}", Toast.LENGTH_LONG).show()
                Log.e("ServiciosFragment", "Error fetching data: ${error.message}", error)
            }
        )

        requestQueue.add(stringRequest)
    }

    private fun onItemSelected(Soporte: Soporte) {
        Toast.makeText(this, Soporte.idSoporte.toString(), Toast.LENGTH_LONG).show()
    }

}