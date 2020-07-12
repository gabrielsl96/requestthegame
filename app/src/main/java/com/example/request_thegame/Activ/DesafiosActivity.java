package com.example.request_thegame.Activ;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.example.request_thegame.Config.ConfiguracaoFirebase;
import com.example.request_thegame.Frag.DiasDesafios.DiaTresFragment;
import com.example.request_thegame.Frag.DiasDesafios.DiaUmFragment;
import com.example.request_thegame.Frag.DiasDesafios.DiasDoisFragment;
import com.example.request_thegame.Frag.DiasDesafios.DiasQuatroFragment;
import com.example.request_thegame.Frag.EstatisticasFragment;
import com.example.request_thegame.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;

public class DesafiosActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Menu menu;
    private Toolbar toolbar;
    private DatabaseReference databaseReference = ConfiguracaoFirebase.getReferenceDatabase().child("DiasDesafios");
    private DiaUmFragment diaUm=new DiaUmFragment();
    private DiasDoisFragment diaDois =new DiasDoisFragment();
    private DiaTresFragment diaTres=new DiaTresFragment();
    private DiasQuatroFragment diaQuatro=new DiasQuatroFragment();
    private EstatisticasFragment estatisticas=new EstatisticasFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                    //Iniciando componentes
                    setContentView(R.layout.activity_desafios);
                    bottomNavigationView=findViewById(R.id.navigationview_bottom);
                    toolbar=findViewById(R.id.toolbar_desafios);


                    setSupportActionBar(toolbar);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowTitleEnabled(false);

                    getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment,estatisticas);
                    bottomNavigationView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("Clique",String.valueOf(v));
                        }
                    });
                    bottomNavigationView.setSelectedItemId(R.id.nav_estatisticas);
                    bottomNavigationView.setOnNavigationItemSelectedListener(navigationItem);
                    bottomNavigationView.setOnNavigationItemReselectedListener(navigationItemReselectedListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItem=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.nav_diaUm:
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, diaUm).commit();

                    break;

                case R.id.nav_diaDois:
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, diaDois).commit();
                    break;

                case R.id.nav_estatisticas:
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, estatisticas).commit();
                    break;

                case R.id.nav_diaTres:
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, diaTres).commit();
                    break;

                case R.id.nav_diaQuatro:
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, diaQuatro).commit();
                    break;
            }

            return true;
        }
    };

    private BottomNavigationView.OnNavigationItemReselectedListener navigationItemReselectedListener =
            new BottomNavigationView.OnNavigationItemReselectedListener() {
                @Override
                public void onNavigationItemReselected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_diaUm:
                            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, diaUm).commit();

                            break;

                        case R.id.nav_diaDois:
                            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, diaDois).commit();
                            break;

                        case R.id.nav_estatisticas:
                            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, estatisticas).commit();
                            break;

                        case R.id.nav_diaTres:
                            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, diaTres).commit();
                            break;

                        case R.id.nav_diaQuatro:
                            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, diaQuatro).commit();
                            break;
                    }
                }
            };

}
