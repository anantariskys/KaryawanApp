package com.example.karyawanapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Karyawan::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun karyawanDao(): KaryawanDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "karyawan_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
