package RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.health.myapplication.R;

import java.util.ArrayList;

// Class ini digunakan untuk menampilkan data pada Pendapatan
public class MyAdapterListReservasi extends RecyclerView.Adapter<MyAdapterListReservasi.MyViewHolder> {
    private Context context;
    private ArrayList<String> nama;
    private ArrayList<String> dokter;
    private ArrayList<String> tanggal;
    private ArrayList<String> antrian;

    public MyAdapterListReservasi(Context context, ArrayList nama, ArrayList dokter, ArrayList tanggal, ArrayList antrian) {
        this.context = context;
        this.nama = nama;
        this.dokter = dokter;
        this.tanggal = tanggal;
        this.antrian = antrian;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row_reservasi,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nama.setText(String.valueOf(nama.get(position)));
        holder.tanggal.setText(String.valueOf(tanggal.get(position)));
        holder.dokter.setText(String.valueOf(dokter.get(position)));
        holder.antrian.setText(String.valueOf(antrian.get(position)));
    }

    @Override
    public int getItemCount() {
        return nama.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nama, dokter, tanggal, antrian;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.tvNama);
            tanggal = itemView.findViewById(R.id.tvTanggal);
            dokter = itemView.findViewById(R.id.tvDokter);
            antrian= itemView.findViewById(R.id.tvAntrian);
        }
    }
}
