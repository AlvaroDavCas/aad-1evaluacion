package edu.iesam.add_1evaluacion.feature.data.local.xml

import android.content.Context
import edu.iesam.add_1evaluacion.R
import edu.iesam.add_1evaluacion.core.serialization.JsonSerializer
import edu.iesam.add_1evaluacion.feature.domain.Car

class CocheXmlLocalDataSource(
    private val context: Context,
    private val serializer: JsonSerializer<List<Car>>
) {

    private val keyCar = "car"

    val sharedPref = context.getSharedPreferences(
        context.getString(R.string.xml_local_data_source), Context.MODE_PRIVATE
    )

    fun save(car: List<Car>) {
        sharedPref.edit().apply {
            putString(keyCar, serializer.toJson(car))
            apply()
        }
    }

    fun findAll(): List<Car> {
        val jsonList = sharedPref.getString(keyCar, null)
        jsonList?.let {
            return serializer.fromJson(keyCar)
        }

        return emptyList()
    }

    fun findById(id: String): Car? {
        return findAll().find { it.id == id }

    }

    fun update(id: String): List<Car> {
        val updateList = findAll().filterNot { it.id == id }
        save(updateList)
        return updateList

    }

    fun deleteById(id: String): List<Car> {

        return findAll().filterNot { it.id == id }

    }

    fun delete() {
        sharedPref.edit().apply {
            remove(keyCar)
            apply()
        }
    }


}