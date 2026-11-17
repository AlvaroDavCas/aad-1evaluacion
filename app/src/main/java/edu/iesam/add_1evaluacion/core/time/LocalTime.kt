package edu.iesam.add_1evaluacion.core.time

interface CurrentTimeLong {
    fun currentTimeLong(): Long
}

class LocalTime : CurrentTimeLong {
    override fun currentTimeLong(): Long {
        return System.currentTimeMillis()
    }
}