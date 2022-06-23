package id.creatodidak.dumaspresisi;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {

    private static final String TAG = Home.class.getSimpleName();
    Session session;
    String nama, alamat, nik, hp;
    private BottomNavigationView bottomNavigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new Session(Home.this);
        if (!session.isLoggedIn()) {
            moveToLogin();
        }

        String SSnik = session.getUserDetail().get(Session.NIK);


        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigation.inflateMenu(R.menu.bottom_navigation_items);
        fragmentManager = getSupportFragmentManager();

        //Untuk inisialisasi fragment pertama kali
        fragmentManager.beginTransaction().replace(R.id.main_container, new beranda()).commit();

        //Memberikan listener saat menu item di bottom navigation diklik
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.beranda:
                        fragment = new beranda();
                        break;
                    case R.id.lapor:
                        fragment = new lapor();
                        break;
                    case R.id.progress:
                        fragment = new progressLapor();
                        break;
                    case R.id.saran:
                        fragment = new saran();
                        break;

                }
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).commit();
                return true;
            }

        });
    }

    private void moveToLogin() {
    }
}