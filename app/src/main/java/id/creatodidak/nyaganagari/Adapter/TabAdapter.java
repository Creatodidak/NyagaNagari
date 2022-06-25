package id.creatodidak.nyaganagari.Adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import id.creatodidak.nyaganagari.Fragment.BantuanFr;
import id.creatodidak.nyaganagari.Fragment.BeritaFr;
import id.creatodidak.nyaganagari.Fragment.MenuFr;
import id.creatodidak.nyaganagari.Fragment.PelayananFr;
import id.creatodidak.nyaganagari.Fragment.PenmasFr;

/**
 * Created by BRIPDA ANGGI PERIANTO on 24,June,2022 CREATODIDAK anggiperianto41ays@gmail.com
 **/
public class TabAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    public TabAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                BeritaFr beritaFr = new BeritaFr();
                return beritaFr;
            case 1:
                MenuFr menuFr = new MenuFr();
                return menuFr;
            case 2:
                PelayananFr pelayananFr = new PelayananFr();
                return pelayananFr;
            case 3:
                PenmasFr penmasFr = new PenmasFr();
                return penmasFr;
            case 4:
                BantuanFr bantuanFr = new BantuanFr();
                return bantuanFr;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}