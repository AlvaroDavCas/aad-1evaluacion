package edu.iesam.add_1evaluacion.feature.car.data.local.xml

import android.content.Context
import edu.iesam.add_1evaluacion.R
import edu.iesam.add_1evaluacion.core.cache.CacheTTL
import edu.iesam.add_1evaluacion.core.serialization.JsonSerializer
import edu.iesam.add_1evaluacion.feature.car.domain.Car

class CocheXmlLocalDataSource(
    private val context: Context,
    private val serializer: JsonSerializer<List<Car>>,
    private val cacheTTL: CacheTTL
) {

    companion object {
        private val keyCar = "car"
        private val ttl: Long = 2 * 1000
    }

    val sharedPref = context.getSharedPreferences(
        context.getString(R.string.car_xml_local_data_source), Context.MODE_PRIVATE
    )

    fun save(car: List<Car>) {
        sharedPref.edit().apply {
            putString(keyCar, serializer.toJson(car))
            apply()
        }

        cacheTTL.updateTime(keyCar, ttl)

    }

    fun findAll(): List<Car> {

        if (!cacheTTL.expired(keyCar)) {
            val jsonList = sharedPref.getString(keyCar, null)
            jsonList?.let {
                return serializer.fromJson(keyCar)
            }
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