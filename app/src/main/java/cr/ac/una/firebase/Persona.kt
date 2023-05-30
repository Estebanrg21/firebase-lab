package cr.ac.una.firebase

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Persona(
    var key: String,
    var cedula: String,
    var nombre : String) {
    constructor() : this(key= "",cedula = "",nombre="") {

    }
}