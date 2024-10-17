package com.example.karyawanapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "karyawan")
data class Karyawan(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var nama: String,
    var posisi: String,
    var gaji: Int
)
