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

import id.creatodidak.nyaganagari.BacaBerita;
import id.creatodidak.nyaganagari.Models.BeritaItem;
import id.creatodidak.nyaganagari.R;


/**
 * Created by BRIPDA ANGGI PERIANTO on 06,June,2022 CREATODIDAK anggiperianto41ays@gmail.com
 **/

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.ViewHolder> {

    private final List<BeritaItem> BeritaList;

    public BeritaAdapter(List<BeritaItem> BeritaList) {
        this.BeritaList = BeritaList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_berita, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tanggal.setText(BeritaList.get(position).getTanggal());
        holder.judul.setText(BeritaList.get(position).getJudul());
        holder.satker.setText(BeritaList.get(position).getSatker()+" ("+BeritaList.get(position).getPublish()+")");
        holder.view.setText(BeritaList.get(position).getView());
        Glide.with(holder.itemView.getContext())
                .load("https://polreslandak.id/media/fotoblog/"+BeritaList.get(position).getFoto())
                .placeholder(R.drawable.nf)
                .into(holder.thb);

        String url = BeritaList.get(position).getUrl();
        holder.thm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BacaBerita.class);
                intent.putExtra("url", url);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return BeritaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tanggal, judul, satker, view;
        ImageView thb;
        CardView thm;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tanggal = itemView.findViewById(R.id.txTanggal);
            judul = itemView.findViewById(R.id.txJudul);
            satker = itemView.findViewById(R.id.txSatker);
            view = itemView.findViewById(R.id.bbView);
            thb = itemView.findViewById(R.id.thumbimg);

            thm = itemView.findViewById(R.id.thumb);



        }
    }
}