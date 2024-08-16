package com.example.alienstage

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ResenaActivity : AppCompatActivity() {
    private lateinit var nota: EditText
    private lateinit var calificacion: EditText
    private lateinit var recomendacion: EditText
    private lateinit var fecha: EditText
    private lateinit var btnAgregar: Button
    private lateinit var requestQueue: RequestQueue
    private val url = "https://ladetechnologies.com/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_resena)
        val intent = intent
        val userId = intent.getStringExtra("USER_ID")
        // Inicializa las vistas
        btnAgregar = findViewById(R.id.btnAgregar)
        nota =findViewById(R.id.etNota)
        calificacion= findViewById(R.id.etCalificacion)
        recomendacion = findViewById(R.id.etRecomendacion)
        fecha = findViewById(R.id.etFecha)
        requestQueue = Volley.newRequestQueue(this)




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
        fecha.setOnClickListener {
            showDatePickerDialog()
        }
        btnAgregar.setOnClickListener {
            agregarContacto()
        }

    }
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            R.style.CustomDatePickerDialog,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                fecha.setText(dateFormat.format(calendar.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
    private fun limpiarCampos() {
        nota.setText("")
        calificacion.setText("")
        recomendacion.setText("")
        fecha.setText("")

    }
    private fun agregarContacto() {
        val notaText = nota.text.toString()
        val calificacionText = calificacion.text.toString()
        val recomendacionText = recomendacion.text.toString()
        val fechaText = fecha.text.toString()
        val idPaquete ="1"
        val estatus="Nuevo"

        if (notaText.isNotEmpty() && calificacionText.isNotEmpty() && recomendacionText.isNotEmpty()  && fechaText.isNotEmpty()) {
            val url = "${this.url}insertaresenas.php"
            ejecutarWebService(url,notaText,calificacionText,recomendacionText,estatus, fechaText,idPaquete)
            limpiarCampos()
        } else {
            Toast.makeText(this, "Todos los campos son obligatorios.", Toast.LENGTH_SHORT).show()
        }
    }
    private fun ejecutarWebService(url:String,nota:String ,calificacion: String ,recomendacion: String,estatus:String, fecha: String, idPaquete:String) {
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            { response ->
                Log.d("VolleyResponse", response.toString())
                Toast.makeText(this, "Su solicitud fue recibida con éxito en se le dara el seguimiento correspondiente", Toast.LENGTH_LONG).show()
            },
            { error ->
                if (error.networkResponse != null) {
                    Log.e("VolleyError", "Response code: ${error.networkResponse.statusCode}")
                    Log.e("VolleyError", "Response body: ${String(error.networkResponse.data)}")
                }
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_LONG).show()
                Log.e("VolleyError", error.toString())

            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["nota"] = nota
                params["calificacion"] = calificacion
                params["recomendacion"] = recomendacion
                params["estatus"] = estatus
                params["fecha"] = fecha
                params["idPaquete"] = idPaquete
                return params
            }
        }
        requestQueue.add(stringRequest)
    }
}