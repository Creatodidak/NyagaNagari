package id.creatodidak.nyaganagari.Fragment;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;

import com.google.android.material.tabs.TabLayout;

import id.creatodidak.nyaganagari.DumasPolres;
import id.creatodidak.nyaganagari.R;
import id.creatodidak.nyaganagari.wviews.wvIDEN;
import id.creatodidak.nyaganagari.wviews.wvSIM;
import id.creatodidak.nyaganagari.wviews.wvSKCK;
import id.creatodidak.nyaganagari.wviews.wvSPKT;
import id.creatodidak.nyaganagari.wviews.wvVAKSIN;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PelayananFr#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PelayananFr extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PelayananFr() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PelayananFr.
     */
    // TODO: Rename and change types and number of parameters
    public static PelayananFr newInstance(String param1, String param2) {
        PelayananFr fragment = new PelayananFr();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pelayanan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView sim, skck, iden, spkt, vaksin, lapor;

        lapor = view.findViewById(R.id.laporkan);
        sim = view.findViewById(R.id.plSIM);
        skck = view.findViewById(R.id.plSKCK);
        iden = view.findViewById(R.id.plIDEN);
        spkt = view.findViewById(R.id.plSPKT);
        vaksin = view.findViewById(R.id.plVAKSIN);

        lapor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DumasPolres.class);
                startActivity(intent);
            }
        });

        sim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), wvSIM.class);
                startActivity(i);
            }
        });

        skck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), wvSKCK.class);
                startActivity(i);
            }
        });

        iden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), wvIDEN.class);
                startActivity(i);
            }
        });
        spkt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), wvSPKT.class);
                startActivity(i);
            }
        });
        vaksin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), wvVAKSIN.class);
                startActivity(i);
            }
        });
    }
}