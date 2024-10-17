import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.karyawanapp.Karyawan
import com.example.karyawanapp.R

class KaryawanAdapter(
    private var karyawanList: List<Karyawan>,
    private val onDeleteClick: (Karyawan) -> Unit,
    private val onUpdateClick: (Karyawan) -> Unit // Callback untuk update
) : RecyclerView.Adapter<KaryawanAdapter.KaryawanViewHolder>() {

    inner class KaryawanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        private val tvPosisi: TextView = itemView.findViewById(R.id.tvPosisi)
        private val tvGaji: TextView = itemView.findViewById(R.id.tvGaji)
        private val btnUpdate: Button = itemView.findViewById(R.id.btnUpdate)
        private val btnHapus: Button = itemView.findViewById(R.id.btnHapus)

        fun bind(karyawan: Karyawan) {
            tvNama.text = karyawan.nama
            tvPosisi.text = karyawan.posisi
            tvGaji.text = karyawan.gaji.toString()

            btnUpdate.setOnClickListener {
                onUpdateClick(karyawan)
            }

            btnHapus.setOnClickListener {
                onDeleteClick(karyawan)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KaryawanViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_karyawan, parent, false)
        return KaryawanViewHolder(view)
    }

    override fun onBindViewHolder(holder: KaryawanViewHolder, position: Int) {
        holder.bind(karyawanList[position])
    }

    override fun getItemCount() = karyawanList.size

    fun updateKaryawanList(newKaryawanList: List<Karyawan>) {
        karyawanList = newKaryawanList
        notifyDataSetChanged()
    }
}
