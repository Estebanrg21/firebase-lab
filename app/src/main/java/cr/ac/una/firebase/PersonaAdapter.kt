package cr.ac.una.firebase

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView

class PersonaAdapter(context: Context, resource: Int,
                     val objects: List<Persona>,
                     val onEdit: (String)->Unit,
                     val onDelete: (String)->Unit
) :
    ArrayAdapter<Persona>(context, resource, objects) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            val inflater = (context as Activity).layoutInflater
            view = inflater.inflate(R.layout.list_item, parent, false)
        }
        val cedula = view?.findViewById<TextView>(R.id.cedula)
        val nombre = view?.findViewById<TextView>(R.id.nombre)
        val editBtn = view?.findViewById<ImageButton>(R.id.edit)
        val deleteBtn = view?.findViewById<ImageButton>(R.id.delete)
        editBtn?.setOnClickListener{
            onEdit(objects[position].key)
        }
        deleteBtn?.setOnClickListener{
            onDelete(objects[position].key)
        }
        cedula?.setText(objects[position].cedula)
        nombre?.setText(objects[position].nombre)
        return view!!
    }
}