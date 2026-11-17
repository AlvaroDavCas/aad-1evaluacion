package edu.iesam.add_1evaluacion.feature.owner.data.local.xml

import android.content.Context
import edu.iesam.add_1evaluacion.R
import edu.iesam.add_1evaluacion.core.serialization.JsonSerializer
import edu.iesam.add_1evaluacion.feature.owner.domain.Owner

class OwnerXmlLocalDataSource(
    private val context: Context,
    private val serializer: JsonSerializer<List<Owner>>
) {


    private val keyOwner = "owner"
    val sharedPref = context.getSharedPreferences(
        context.getString(R.string.owner_xml_local_data_source), Context.MODE_PRIVATE
    )

    fun save(owner: List<Owner>) {
        sharedPref.edit().apply {
            putString(keyOwner, serializer.toJson(owner))
            apply()
        }
    }

    fun findAll(): List<Owner> {
        val jsonList = sharedPref.getString(keyOwner, null)
        jsonList?.let {
            return serializer.fromJson(keyOwner)
        }

        return emptyList()
    }

    fun findById(id: String): Owner? {
        return findAll().find { it.dni == id }
    }

}