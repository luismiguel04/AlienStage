package com.example.alienstage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.alienstage.adaptador.ReseñasAdapeter
import com.example.alienstage.adaptador.SoportesAdapter
import com.example.alienstage.databinding.ActivityListaResenasBinding
import com.example.alienstage.databinding.ActivityListaSoporteBinding
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class ListaResenasActivity : AppCompatActivity() {
    private lateinit var requestQueue: RequestQueue
    private val arrayList = ArrayList<Reseña>()
    private val displayList = ArrayList<Reseña>()
    private lateinit var binding: ActivityListaResenasBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaResenasBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        val intent = intent
        val userId = intent.getStringExtra("idPaquete")

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.inflateMenu(R.menu.menu_lateral)  // Infla el menú en el Toolbar

        // Manejar las opciones del menú
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuReseñas -> {
                    val intent =
                        Intent(this, ResenaActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menuStatus -> {
                    val intent =
                        Intent(this, EstatusActivity::class.java).apply {
                            putExtra("USER_ID", userId) // Pasa el userId al Intent
                        }
                    startActivity(intent)
                    true
                }
                R.id.menuListaSoporte -> {
                    val intent =
                        Intent(this, ListaSoporteActivity::class.java).apply {
                            putExtra("USER_ID", userId) // Pasa el userId al Intent
                        }
                    startActivity(intent)
                    true
                }
                R.id.menuCerrarSecion -> {
                    val intent =
                        Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        if (userId.isNullOrEmpty()) {
            Toast.makeText(this, "Error: No se recibió el ID de usuario.", Toast.LENGTH_LONG).show()
            finish() // Finaliza la actividad si no se recibe el userId
        } else {
            val Id=userId.toString().toInt()
            requestQueue = Volley.newRequestQueue(this)

            fetchSoportes(Id)
        }

    }
    private fun fetchSoportes(Id: Int) {
        val url = "https://ladetechnologies.com/consultareseñas.php?idpaquete=$Id"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    if (response == "0") {
                        // Si la respuesta es "0", no hay reseñas
                        binding.emptyView.visibility = View.VISIBLE
                        binding.rvsoportes.visibility = View.GONE
                    } else {
                        // Procesa la respuesta JSON
                        arrayList.clear()
                        displayList.clear()
                        val jsonArray = JSONArray(response)
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            val fecha = jsonObject.getString("fecha")
                            val nota = jsonObject.getString("nota")
                            val calificacion = jsonObject.getString("calificacion")
                            val recomendacion = jsonObject.getString("recomendacion")
                            val notarespuesta = jsonObject.getString("notarespuesta")
                            val fecharespuesta = jsonObject.getString("fecharespuesta")

                            arrayList.add(Reseña(fecha, nota, calificacion, recomendacion, notarespuesta, fecharespuesta))
                        }
                        displayList.addAll(arrayList)

                        // Actualiza la visibilidad del mensaje de "Sin reseñas"
                        if (displayList.isEmpty()) {
                            binding.emptyView.visibility = View.VISIBLE
                            binding.rvsoportes.visibility = View.GONE
                        } else {
                            binding.emptyView.visibility = View.GONE
                            binding.rvsoportes.visibility = View.VISIBLE
                            binding.rvsoportes.layoutManager = LinearLayoutManager(this)
                            binding.rvsoportes.adapter = ReseñasAdapeter(displayList) { reseña ->
                                onItemSelected(reseña)
                            }
                        }
                    }
                } catch (e: JSONException) {
                    Log.e("JSONError", "Error parsing JSON: ${e.message}")
                    Toast.makeText(this, "Error parsing data: ${e.message}", Toast.LENGTH_LONG).show()
                }
            },
            { error ->
                Toast.makeText(this, "Error fetching data: ${error.message}", Toast.LENGTH_LONG).show()
                Log.e("FetchError", "Error fetching data: ${error.message}", error)
            }
        )

        requestQueue.add(stringRequest)
    }

    private fun onItemSelected(Reseña: Reseña) {

    }
}