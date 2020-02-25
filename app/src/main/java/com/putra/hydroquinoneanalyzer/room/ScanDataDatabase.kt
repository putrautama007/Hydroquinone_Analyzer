package com.putra.hydroquinoneanalyzer.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.putra.hydroquinoneanalyzer.model.ScanModel

@Database(entities = [ScanModel::class], exportSchema = false, version = 1)
abstract class ScanDataDatabase : RoomDatabase() {
    companion object{
        private const val DB_NAME = "concentration_db"
        private val LOCK = Any()
        private var INSTANCE: ScanDataDatabase? = null

        fun getInstance(context: Context): ScanDataDatabase? {
            if (INSTANCE == null) {
                synchronized(LOCK) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ScanDataDatabase::class.java,
                        DB_NAME
                    ).build()
                }
            }
            return INSTANCE as ScanDataDatabase
        }
    }

    abstract fun calculationDao(): CalculationDao?
}