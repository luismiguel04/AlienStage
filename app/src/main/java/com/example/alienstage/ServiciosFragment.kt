
package com.example.alienstage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.alienstage.adaptador.PaquetesAdapter
import com.example.alienstage.databinding.FragmentServiciosBinding
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.Executor
import androidx.biometric.BiometricPrompt




class ServiciosFragment : Fragment(R.layout.fragment_servicios) {

    private var _binding: FragmentServiciosBinding? = null
    private val binding get() = _binding!!
    private lateinit var requestQueue: RequestQueue
    private val arrayList = ArrayList<paquete>()
    private val displayList = ArrayList<paquete>()
    private var userId: String = ""
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServiciosBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userId = arguments?.getString("USER_ID").toString()
        // Configurar el executor y el BiometricPrompt
        executor = ContextCompat.getMainExecutor(requireContext())

        biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
               
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                // Autenticación exitosa, iniciar la actividad EstatusActivity
                val intent = Intent(requireContext(), EstatusActivity::class.java).apply {
                    putExtra("USER_ID", userId) // Pasa el userId al Intent
                }
                startActivity(intent)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(requireContext(), "Autenticación fallida", Toast.LENGTH_SHORT).show()
            }
        })


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
                    showBiometricPrompt()
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
        val url = "https://ladetechnologies.com/consultapaquetes.php"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                arrayList.clear()
                displayList.clear()
                val jsonArray = JSONArray(response)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = JSONObject(jsonArray.getString(i))
                    val id = jsonObject.getInt("idPaquete")
                    val horas = jsonObject.getString("horas")
                    val nombre = jsonObject.getString("nombre")
                    val descripcion = jsonObject.getString("descripcion")
                    val precio = jsonObject.getDouble("precio")
                    val foto = jsonObject.getString("foto")
                    val resena = jsonObject.getString("reseñas")
                    val estatus = jsonObject.getString("estatus")


                    arrayList.add(paquete(id, nombre,horas, descripcion, precio, foto, resena, estatus))

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
    private fun showBiometricPrompt() {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticación biométrica")
            .setSubtitle("Coloque su dedo en el sensor")
            .setNegativeButtonText("Cancelar")
            .build()
        biometricPrompt.authenticate(promptInfo)
    }

    private fun onItemSelected(paquete: paquete) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
