package com.example.karyawanapp

import androidx.room.*

@Dao
interface KaryawanDao {
    @Insert
    suspend fun insert(karyawan: Karyawan)

    @Update
    suspend fun update(karyawan: Karyawan)

    @Delete
    suspend fun delete(karyawan: Karyawan)

    @Query("SELECT * FROM karyawan ORDER BY id DESC")
    suspend fun getAll(): List<Karyawan>

    @Query("SELECT * FROM karyawan WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): Karyawan?
}

