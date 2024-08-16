package com.example.alienstage

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.alienstage.databinding.ActivityDetalleServicioBinding
import com.example.alienstage.databinding.ActivityFormularioServicioBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FormularioServicioActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormularioServicioBinding
    private val url = "https://ladetechnologies.com/"
    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFormularioServicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestQueue = Volley.newRequestQueue(this)


        val descipcion = intent.getStringExtra("descripcion")


        var tipo="Pago en linea"
        var referencia ="2165sdrdr"
        val ubicacion = binding.etUbicacion.text.toString()

        val fecha = binding.etFecha.text.toString()
        val hora =binding.etHora.text.toString()
        val duracion = intent.getStringExtra("horas").toString()
        val estatus ="Nuevo"


        val cantidad = intent.getStringExtra("cantidad").toString()
        val idRuta ="1"
        val nombreru ="AMG"
        var idServicio ="1"
        var nombreser ="Servicios de Sonido"
        val idPaquete = intent.getStringExtra("idPaquete").toString()
        val nombrep = intent.getStringExtra("nombrep").toString()

        val idWeb = GlobalData.idWeb.toString()
        val nombre = GlobalData.nombreusuario.toString()
        val apellido = GlobalData.apellido.toString()
        val correo = GlobalData.correo.toString()

        val precio = cantidad?.toDouble() ?: 0.0
        val formatoMoneda = NumberFormat.getCurrencyInstance(Locale.getDefault())
        val precioFormateado = formatoMoneda.format(precio)
        binding.precio.text = precioFormateado

        binding.nombrePaquete.text=nombrep
        binding.descripcion.text=descipcion+" incluye "+ duracion +" horas."





        // Configura DatePickerDialog para la fecha
        binding.etFecha.setOnClickListener {
            showDatePickerDialog()
        }
        binding.etHora.setOnClickListener {
            showTimePickerDialog()
        }
        // Configura el botón de agregar
        binding.btnAgregar.setOnClickListener {

            val url = "${this.url}insercionpagos.php"
            ejecutarWebService(url,tipo,referencia,ubicacion,fecha,hora,duracion,estatus,cantidad,idRuta,nombreru,idServicio,nombreser,idPaquete,nombrep,idWeb,nombre,apellido,correo)
        }




    }

    private fun ejecutarWebService(url:String,tipo:String,referencia:String, ubicacion:String,fecha:String, hora:String, duracion:String, estatus:String, cantidad:String, idRuta:String, nombreru:String, idServicio:String, nombreser:String, idPaquete:String, nombrep:String, idWeb:String, nombre:String, apellido:String,correo:String) {
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            { response ->
                Log.d("VolleyResponse", response.toString())
                Toast.makeText(this, "Su solicitud fue recibida con éxito en se le dara el seguimiento correspondiente", Toast.LENGTH_LONG).show()
            },
            { error ->
                Log.e("VolleyError", error.toString())
                Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["tipo"] = tipo
                params["referencia"] = referencia
                params["ubicacion"] = ubicacion
                params["fecha"] = fecha
                params["hora"] = hora
                params["duracion"] = duracion
                params["estatus"] = estatus
                params["cantidad"] = cantidad
                params["idRuta"] = idRuta
                params["nombreru"] = nombreru
                params["idServicio"] = idServicio
                params["nombreser"] = nombreser
                params["idPaquete"] = idPaquete
                params["nombrep"] = nombrep
                params["idWeb"] = idWeb
                params["nombre"] = nombre
                params["apellido"] = apellido
                params["correo"] = correo
                return params
            }
        }
        requestQueue.add(stringRequest)
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            R.style.CustomDatePickerDialog,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                binding.etFecha.setText(dateFormat.format(calendar.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            R.style.CustomDatePickerDialog,
            { _, hourOfDay, minute ->
                val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                val timeString = timeFormat.format(calendar.time)
                binding.etHora.setText(timeString)
            },
            hour,
            minute,
            true // true for 24-hour format
        )

        timePickerDialog.show()
    }
}