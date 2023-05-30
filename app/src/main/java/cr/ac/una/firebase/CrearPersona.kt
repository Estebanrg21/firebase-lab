package cr.ac.una.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import com.google.firebase.FirebaseApp

class CrearPersona : AppCompatActivity() {
    val fbViewModel: FirebaseViewModel
            by viewModels {
                FirebaseViewModel.FirebaseViewModelFactory(
                    FirebaseApp
                        .initializeApp(this)!!
                )
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val toEditKey = intent.getStringExtra("toEdit")
        setContentView(R.layout.activity_crear_persona)
        val cedula = findViewById<EditText>(R.id.cedula)
        val nombre = findViewById<EditText>(R.id.nombre)
        val button = findViewById<Button>(R.id.send)
        if (toEditKey != null) {
            fbViewModel.getPersona(toEditKey) {
                cedula.setText(it.cedula)
                nombre.setText(it.nombre)
            }
        }
        button.setOnClickListener {
            fbViewModel.savePersona(
                Persona(
                    key = toEditKey ?: "",
                    cedula = cedula.text.toString(),
                    nombre = nombre.text.toString()
                )
            )
            finish()
        }
    }
}