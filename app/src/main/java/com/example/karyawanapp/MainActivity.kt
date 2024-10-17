package com.example.karyawanapp


import KaryawanAdapter
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel: KaryawanViewModel by viewModels()
    private lateinit var etNama: EditText
    private lateinit var etPosisi: EditText
    private lateinit var etGaji: EditText
    private lateinit var btnSimpan: Button
    private lateinit var rvKaryawan: RecyclerView
    private lateinit var adapter: KaryawanAdapter
    private var currentKaryawan: Karyawan? = null // Menyimpan karyawan yang sedang diedit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etNama = findViewById(R.id.etNama)
        etPosisi = findViewById(R.id.etPosisi)
        etGaji = findViewById(R.id.etGaji)
        btnSimpan = findViewById(R.id.btnSimpan)
        rvKaryawan = findViewById(R.id.rvKaryawan)

        adapter = KaryawanAdapter(emptyList(), { karyawan ->
            viewModel.deleteKaryawan(karyawan)
            Toast.makeText(this, "Karyawan dihapus", Toast.LENGTH_SHORT).show()
        }, { karyawan ->
            showUpdateDialog(karyawan) // Tampilkan dialog untuk update
        })

        rvKaryawan.adapter = adapter
        rvKaryawan.layoutManager = LinearLayoutManager(this)

        btnSimpan.setOnClickListener {
            if (validateInput()) {
                val nama = etNama.text.toString()
                val posisi = etPosisi.text.toString()
                val gaji = etGaji.text.toString().toInt()

                if (currentKaryawan != null) { // Jika ada karyawan yang sedang diedit
                    currentKaryawan?.let {
                        it.nama = nama
                        it.posisi = posisi
                        it.gaji = gaji
                        viewModel.updateKaryawan(it)
                    }
                    currentKaryawan = null // Reset setelah update
                } else {
                    val karyawan = Karyawan(nama = nama, posisi = posisi, gaji = gaji)
                    viewModel.insertKaryawan(karyawan)
                }
                clearInput()
            }
        }

        viewModel.karyawanList.observe(this) { karyawans ->
            adapter.updateKaryawanList(karyawans)
        }

        viewModel.refreshList()
    }

    private fun showUpdateDialog(karyawan: Karyawan) {
        currentKaryawan = karyawan
        etNama.setText(karyawan.nama)
        etPosisi.setText(karyawan.posisi)
        etGaji.setText(karyawan.gaji.toString())
    }

    private fun validateInput(): Boolean {
        return when {
            etNama.text.isEmpty() -> {
                etNama.error = "Nama tidak boleh kosong"
                false
            }
            etPosisi.text.isEmpty() -> {
                etPosisi.error = "Posisi tidak boleh kosong"
                false
            }
            etGaji.text.isEmpty() -> {
                etGaji.error = "Gaji tidak boleh kosong"
                false
            }
            else -> true
        }
    }

    private fun clearInput() {
        etNama.text.clear()
        etPosisi.text.clear()
        etGaji.text.clear()
    }
}
