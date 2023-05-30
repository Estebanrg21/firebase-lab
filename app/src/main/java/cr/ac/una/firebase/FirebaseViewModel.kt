package cr.ac.una.firebase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class FirebaseViewModel(
    val firebaseApp: FirebaseApp
) : ViewModel() {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val personaRef = database.getReference("persona")
    private var _personas: MutableLiveData<List<Persona>> = MutableLiveData()
    var personas: LiveData<List<Persona>> = _personas
    val databaseListener = personaRef.addValueEventListener(object : ValueEventListener {
        override fun onCancelled(error: DatabaseError) {
            //NOTHING TO DO
        }

        override fun onDataChange(snapshot: DataSnapshot) {
            val list = ArrayList<Persona>()
            snapshot.children.forEach {data ->
                val persona = data.getValue<Persona>() as Persona
                persona.key = data.key.toString()
                list.add(persona)
            }
            _personas.postValue(list)
            println(list)
        }
    })

    fun savePersona(persona: Persona) {
        var personaId = persona.key
        if (personaId.isEmpty()) {
            personaId = personaRef.push().key!!
        }
        personaRef.child(personaId).setValue(persona)
    }

    fun getPersona(key: String,callback:(Persona)->Unit ) {
        personaRef.child(key).get().addOnSuccessListener {
            callback(it.getValue<Persona>()!!)
        }
    }

    fun editarPersona(persona: Persona) {
        personaRef.child(persona.key).setValue(persona)
    }

    fun deletePersona(key: String) {
        personaRef.child(key).removeValue()
    }

    class FirebaseViewModelFactory(private var firebaseApp: FirebaseApp) :
        ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FirebaseViewModel(firebaseApp) as T
        }
    }
}