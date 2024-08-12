import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.alienstage.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class SoporteFragment : Fragment(R.layout.fragment_soporte) {

    private lateinit var correo: EditText
    private lateinit var situacion: EditText
    private lateinit var descripcion: EditText
    private lateinit var fecha: TextInputEditText
    private lateinit var btnCapturarEvidencia: Button
    private lateinit var btnAgregar: Button
    private lateinit var requestQueue: RequestQueue
    private val url = "http://192.168.0.106/"

    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_CAMERA_PERMISSION = 2
    private var currentPhotoPath: String = ""
    // Usar el ID recibido
    private var userId: String = ""




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        // Recupera el ID del Bundle
        userId = arguments?.getString("USER_ID").toString()

        // Inicializa las vistas
        btnAgregar = view.findViewById(R.id.btnAgregar)
        correo = view.findViewById(R.id.etCorreo)
        situacion = view.findViewById(R.id.etSituacion)
        descripcion = view.findViewById(R.id.etDescripcion)
        fecha = view.findViewById(R.id.etFecha)
        btnCapturarEvidencia = view.findViewById(R.id.btnCapturarEvidencia)
        val floatingActionButton: FloatingActionButton = view.findViewById(R.id.floating_action_button)

        // Inicializa Volley RequestQueue
        requestQueue = Volley.newRequestQueue(requireContext())

        // Configura DatePickerDialog para la fecha
        fecha.setOnClickListener {
            showDatePickerDialog()
        }

        // Configura el click para abrir la cámara al presionar el botón "Capturar Evidencia"
        btnCapturarEvidencia.setOnClickListener {
            if (checkAndRequestPermissions()) {
                startForActivityResult.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
            }
        }

        // Configura el botón de agregar
        btnAgregar.setOnClickListener {
            agregarContacto()
        }
        floatingActionButton.setOnClickListener {
            val phoneNumber = "3338007937"
            val url = "https://api.whatsapp.com/send?phone=$phoneNumber"

            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Error al abrir WhatsApp", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private val startForActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == AppCompatActivity.RESULT_OK) {
                Toast.makeText(requireContext(), "Fotografía tomada!!!", Toast.LENGTH_SHORT).show()
                val extras = activityResult.data?.extras
                val imgBitmap = extras?.get("data") as Bitmap?
                if (imgBitmap != null) {
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    imgBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                    val byteArray = byteArrayOutputStream.toByteArray()
                    val fotobase64 = Base64.encodeToString(byteArray, Base64.DEFAULT)
                    currentPhotoPath=fotobase64
                } else {
                    Toast.makeText(requireContext(), "Error al capturar la imagen", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Proceso cancelado", Toast.LENGTH_SHORT).show()
            }
        }

    private fun checkAndRequestPermissions(): Boolean {
        val cameraPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)

        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA_PERMISSION
            )
            return false
        }
        return true
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startForActivityResult.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
            } else {
                Toast.makeText(requireContext(), "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 100
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
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



    private fun agregarContacto() {
        val correoText = correo.text.toString()
        val descripcionText = descripcion.text.toString()
        val situacionText = situacion.text.toString()
        val fechaText = fecha.text.toString()

        if (correoText.isNotEmpty() && descripcionText.isNotEmpty() && situacionText.isNotEmpty() && fechaText.isNotEmpty()) {
            val url = "${this.url}insercionsoporte.php"
            ejecutarWebService(url, correoText, situacionText, descripcionText, fechaText, currentPhotoPath,userId)
            limpiarCampos()
        } else {
            Toast.makeText(requireContext(), "Todos los campos son obligatorios.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun ejecutarWebService(url: String, correo: String, situacion: String, descripcion: String, fecha: String, evidencia: String, idweb:String) {
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            { response ->
                Log.d("VolleyResponse", response.toString())
                Toast.makeText(requireContext(), "Su solicitud fue recibida con éxito en se le dara el seguimiento correspondiente", Toast.LENGTH_LONG).show()
            },
            { error ->
                Log.e("VolleyError", error.toString())
                Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["correo"] = correo
                params["situacion"] = situacion
                params["descripcion"] = descripcion
                params["fecha"] = fecha
                params["evidencia"] = evidencia
                params["idweb"] = userId
                return params
            }
        }
        requestQueue.add(stringRequest)
    }


    private fun limpiarCampos() {
        correo.setText("")
        descripcion.setText("")
        situacion.setText("")
        fecha.setText("")

    }


}