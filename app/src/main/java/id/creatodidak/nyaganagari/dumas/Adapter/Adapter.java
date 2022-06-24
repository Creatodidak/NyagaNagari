package id.creatodidak.nyaganagari.dumas.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.creatodidak.nyaganagari.dumas.Model.prog.ProgressItem;
import id.creatodidak.nyaganagari.R;


/**
 * Created by BRIPDA ANGGI PERIANTO on 06,June,2022 CREATODIDAK anggiperianto41ays@gmail.com
 **/

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private final List<ProgressItem> SMasukList;

    public Adapter(List<ProgressItem> SMasukList) {
        this.SMasukList = SMasukList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listprogress, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tanggal.setText(SMasukList.get(position).getTanggal() + " Pukul" + SMasukList.get(position).getTanggal());
        holder.progress.setText(SMasukList.get(position).getTimeline());
        holder.handler.setText("Diupdate Oleh: " + SMasukList.get(position).getHandler());

    }

    @Override
    public int getItemCount() {
        return SMasukList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tanggal, progress, handler;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tanggal = itemView.findViewById(R.id.tanggal);
            progress = itemView.findViewById(R.id.progresstx);
            handler = itemView.findViewById(R.id.handler);
        }
    }
}