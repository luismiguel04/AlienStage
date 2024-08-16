package com.example.alienstage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONException

class LoginActivity : AppCompatActivity() {
    private lateinit var telefono:TextView
    private lateinit var contrasena :TextView
    private lateinit var login : Button
    private val url = "https://ladetechnologies.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        telefono = findViewById(R.id.etTelefono)
        contrasena = findViewById(R.id.etContraseña)
        login =findViewById(R.id.btnLogin)

        login.setOnClickListener({buscarUsuario()})



    }


    private fun buscarUsuario() {
        // Instancia que recibe la información del servidor
        val cliente = AsyncHttpClient()
        // Llamada al archivo PHP
        cliente.get(
            url + "busquedalogin.php?telefono=" +
                    telefono.text + "&contrasena=" + contrasena.text,
            object : AsyncHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<Header>?,
                    responseBody: ByteArray?
                ) {
                    // El código 200 indica que hubo registros
                    if (statusCode == 200) {
                        try {
                            val responseStr = responseBody?.let { String(it) } ?: ""
                            // Si existen registros como resultado de la búsqueda
                            if (responseStr != "0") {
                                // Recibe la información y coloca en el arreglo JSON
                                val contacto = JSONArray(responseStr)
                                // Colocar la información en las cajas de texto
                                if (contacto.length() > 0) {
                                    val primerContacto = contacto.getJSONObject(0)
                                    val id =(primerContacto.getString("idWeb"))
                                    val nombre =(primerContacto.getString("nombre"))
                                    val apellido =(primerContacto.getString("apellido"))
                                    val correo =(primerContacto.getString("correo"))
                                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                    intent.putExtra("USER_ID", id)
                                    GlobalData.nombreusuario = nombre
                                    GlobalData.idWeb = id
                                    GlobalData.apellido = apellido
                                    GlobalData.correo = correo
                                    startActivity(intent)
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Bienvenido $nombre $id",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Revise los datos ingresados.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Revise los datos ingresados.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } catch (e: JSONException) {
                            Toast.makeText(
                                this@LoginActivity,
                                "Error al obtener información.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Sin resultados en búsqueda.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<Header>?,
                    responseBody: ByteArray?,
                    error: Throwable?
                ) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Error: ${error?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

}