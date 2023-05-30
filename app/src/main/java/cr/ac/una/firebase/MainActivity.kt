package cr.ac.una.firebase

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    val fbViewModel: FirebaseViewModel
            by viewModels {
                FirebaseViewModel.FirebaseViewModelFactory(
                    FirebaseApp
                        .initializeApp(this)!!
                )
            }
    private lateinit var personas :List<Persona>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        personas = mutableListOf<Persona>()
        setContentView(R.layout.activity_main)
        val lista = findViewById<ListView>(R.id.list)
        findViewById<Button>(R.id.crear_btn).setOnClickListener {
            startActivity(Intent(this, CrearPersona::class.java))
        }
        fbViewModel.personas.observe(this) { elementos ->
            personas = elementos
            lista.adapter = PersonaAdapter(
                this, 0,personas,
                this::editPersona, fbViewModel::deletePersona
            )
        }

//        // Inicializar Firebase
//        FirebaseApp.initializeApp(this)
//
//        // Obtener referencia a la base de datos "personas"
//        val database = FirebaseDatabase.getInstance()
//        personasRef = database.getReference("persona")
//
//        // Agregar una persona a la base de datos
//        val persona = Persona("Allam Chaves")
//        val personaId = personasRef.push().key
//        personasRef.child(personaId!!).setValue(persona)
    }

    fun editPersona(key:String) {
        startActivity(Intent(this, CrearPersona::class.java).apply {
            putExtra("toEdit", key)
        })
    }

}