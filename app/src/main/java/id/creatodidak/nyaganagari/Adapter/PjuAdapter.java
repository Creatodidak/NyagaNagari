package id.creatodidak.nyaganagari.Adapter;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Locale;

import id.creatodidak.nyaganagari.BacaBerita;
import id.creatodidak.nyaganagari.Models.PejabatItem;
import id.creatodidak.nyaganagari.R;


/**
 * Created by BRIPDA ANGGI PERIANTO on 06,June,2022 CREATODIDAK anggiperianto41ays@gmail.com
 **/

public class PjuAdapter extends RecyclerView.Adapter<PjuAdapter.ViewHolder> {

    private final List<PejabatItem> PejabatList;

    public PjuAdapter(List<PejabatItem> PejabatList) {
        this.PejabatList = PejabatList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_pejabat, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nama.setText(PejabatList.get(position).getPangkat().toUpperCase(Locale.ROOT)+" "+PejabatList.get(position).getNama().toUpperCase(Locale.ROOT));
        holder.jabatan.setText(PejabatList.get(position).getJabatan().toUpperCase(Locale.ROOT));
        holder.ttl.setText("LAHIR "+PejabatList.get(position).getTtl().toUpperCase(Locale.ROOT));
        holder.diktuk.setText("ALUMNI "+PejabatList.get(position).getDiktuk().toUpperCase(Locale.ROOT));
        holder.jabter.setText("JABATAN TERAKHIR : \n"+PejabatList.get(position).getJabatanterakhir().toUpperCase(Locale.ROOT));
        Glide.with(holder.itemView.getContext())
                .load("https://polreslandak.id/media/fotopejabatx/"+PejabatList.get(position).getFoto())
                .placeholder(R.drawable.nf)
                .into(holder.thmb);
    }

    @Override
    public int getItemCount() {
        return PejabatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nama, jabatan, ttl, diktuk, jabter;
        ImageView thmb;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nama = itemView.findViewById(R.id.pjNama);
            jabatan = itemView.findViewById(R.id.pjJabatan);
            ttl = itemView.findViewById(R.id.pjTtl);
            diktuk = itemView.findViewById(R.id.pjDiktuk);
            jabter = itemView.findViewById(R.id.pjJabTerakhir);
            thmb = itemView.findViewById(R.id.pjFoto);



        }
    }
}