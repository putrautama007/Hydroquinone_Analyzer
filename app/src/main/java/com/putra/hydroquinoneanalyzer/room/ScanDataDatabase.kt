package com.putra.hydroquinoneanalyzer.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class ScanDataDatabase : RoomDatabase() {
    companion object{
        private const val DB_NAME = "concentration_db"
        private val LOCK = Any()
        private var instance: ScanDataDatabase? = null
    }

    @Synchronized
    open fun getInstance(context: Context): ScanDataDatabase? {
        if (instance == null) {
            synchronized(LOCK) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    ScanDataDatabase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration().build()
            }
        }
        return instance
    }

    abstract fun calculationDao(): CalculationDao?
}