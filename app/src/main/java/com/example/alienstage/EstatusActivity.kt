package com.example.alienstage

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException

class EstatusActivity : AppCompatActivity() {
    private lateinit var requestQueue: RequestQueue


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estatus) // Verifica que este archivo XML existe y contiene las vistas con los IDs correctos
        enableEdgeToEdge()

        val userId = intent.getStringExtra("USER_ID")
        if (userId != null) {
            try {
                val id = userId.toInt()
                requestQueue = Volley.newRequestQueue(this)
                fetchSoportes(id)
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "El USER_ID es inválido.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "No se proporcionó el USER_ID.", Toast.LENGTH_SHORT).show()
        }

    }

    private fun fetchSoportes(id: Int) {
        val url = "https://ladetechnologies.com/busquedausuario.php?idweb=$id"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    val jsonArray = JSONArray(response)
                    if (jsonArray.length() > 0) {
                        val jsonObject = jsonArray.getJSONObject(0)

                        // Verifica que las vistas están correctamente inicializadas antes de asignarles valores
                        val textCorreo = findViewById<TextView>(R.id.textCorreo)
                        val textContrasena = findViewById<TextView>(R.id.textContrasena)
                        val textTelefono = findViewById<TextView>(R.id.textTelefono)
                        val textNombre = findViewById<TextView>(R.id.textNombre)
                        val textApellido = findViewById<TextView>(R.id.textApellido)
                        val textEstatus = findViewById<TextView>(R.id.textEstatus)
                        val textFecha = findViewById<TextView>(R.id.textFecha)

                        if (textCorreo != null && textContrasena != null && textTelefono != null &&
                            textNombre != null && textApellido != null && textEstatus != null && textFecha != null) {

                            val correo = jsonObject.optString("correo", "No disponible")
                            val contrasena = jsonObject.optString("contraseña", "No disponible")
                            val telefono = jsonObject.optString("telefono", "No disponible")
                            val nombre = jsonObject.optString("nombre", "No disponible")
                            val apellido = jsonObject.optString("apellido", "No disponible")
                            val estatus = jsonObject.optString("estatus", "No disponible")
                            val fecha = jsonObject.optString("fecha", "No disponible")

                            textCorreo.text = "Correo: $correo"
                            textContrasena.text = "Contraseña: $contrasena"
                            textTelefono.text = "Teléfono: $telefono"
                            textNombre.text = "Nombre: $nombre"
                            textApellido.text = "Apellido: $apellido"
                            textEstatus.text = "Estatus: $estatus"
                            textFecha.text = "Fecha: $fecha"
                        } else {
                            Log.e("EstatusActivity", "Algunas vistas no se pudieron inicializar.")
                        }
                    } else {
                        Toast.makeText(this, "No se encontraron datos.", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    Toast.makeText(this, "Error parsing JSON: ${e.message}", Toast.LENGTH_LONG).show()
                    Log.e("EstatusActivity", "Error parsing JSON: ${e.message}", e)
                }
            },
            { error ->
                Toast.makeText(this, "Error fetching data: ${error.message}", Toast.LENGTH_LONG).show()
                Log.e("EstatusActivity", "Error fetching data: ${error.message}", error)
            }
        )

        requestQueue.add(stringRequest)
    }



}
