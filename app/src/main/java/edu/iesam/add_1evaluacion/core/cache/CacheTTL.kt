package edu.iesam.add_1evaluacion.core.cache

import android.content.Context
import edu.iesam.add_1evaluacion.R
import edu.iesam.add_1evaluacion.core.time.LocalTime


interface AppCache {

    fun updateTime(key: String, ttl: Long)
    fun expired(key: String): Boolean

}

class CacheTTL(
    private val context: Context,
    private val localTime: LocalTime
) : AppCache {

    val sharedPref = context.getSharedPreferences(
        context.getString(R.string.cacheTTL), Context.MODE_PRIVATE
    )

    override fun updateTime(key: String, ttl: Long) {
        sharedPref.edit().apply {
            putLong(key, localTime.currentTimeLong() + ttl)
            apply()
        }
    }

    override fun expired(key: String): Boolean {
        val timeSave = sharedPref.getLong(key, 0)
        return localTime.currentTimeLong() > timeSave
    }
}