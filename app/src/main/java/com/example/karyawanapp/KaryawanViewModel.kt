package com.example.karyawanapp


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class KaryawanViewModel(application: Application) : AndroidViewModel(application) {
    private val karyawanDao = AppDatabase.getDatabase(application).karyawanDao()
    val karyawanList = MutableLiveData<List<Karyawan>>()  // LiveData untuk daftar karyawan

    fun insertKaryawan(karyawan: Karyawan) = viewModelScope.launch {
        karyawanDao.insert(karyawan)
        refreshList()  // Refresh setelah insert
    }

    fun updateKaryawan(karyawan: Karyawan) = viewModelScope.launch {
        karyawanDao.update(karyawan)
        refreshList()  // Refresh setelah update
    }

    fun deleteKaryawan(karyawan: Karyawan) = viewModelScope.launch {
        karyawanDao.delete(karyawan)
        refreshList()  // Refresh setelah delete
    }

    fun refreshList() = viewModelScope.launch {
        karyawanList.postValue(karyawanDao.getAll())
    }

    suspend fun getKaryawanById(id: Int): Karyawan? = karyawanDao.getById(id)
}

