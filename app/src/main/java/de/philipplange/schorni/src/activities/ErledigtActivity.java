package de.philipplange.schorni.src.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import de.philipplange.schorni.R;
import de.philipplange.schorni.src.adapter.ErledigteKehrungenAdapter;
import de.philipplange.schorni.src.hilfsklassen.ListenKoordinator;
import de.philipplange.schorni.src.models.Kehrung;

/**
 * Created by Philipp on 04.07.2017.
 */

public class ErledigtActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private Toolbar mToolbar;
    private NavigationView nvDrawer;
    private ListView listView;

    ErledigteKehrungenAdapter adapter;
    ArrayList<Kehrung> liste;
    ListenKoordinator koordinator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erledigt);

        // Liste mit erledigten Kehrungen erstellen
        koordinator = new ListenKoordinator(this);
        liste = koordinator.erledigteKehrungen();


        // Sidemenu vorbereitung TODO in Funktion auslagern
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Erledigt");

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set up Side Menu
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        nvDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case (R.id.nav_auftraege):
                        Intent auftraegeActivity = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(auftraegeActivity);
                        finish();
                        break;
                    case (R.id.nav_erledigt):
                        Intent erledigtActivity = new Intent(getApplicationContext(), ErledigtActivity.class);
                        startActivity(erledigtActivity);
                        finish();
                        break;
                }
                return true;
            }
        });

        // befüllt die Liste mit den erledigten Aufträgen
        adapter = new ErledigteKehrungenAdapter(this, liste);
        listView = (ListView) findViewById(R.id.lvErledigt);
        listView.setAdapter(adapter);

        // Die Listeneinträge werden clickbar gemacht
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Kehrung kehrung = (Kehrung) adapterView.getItemAtPosition(i);
                Intent detailActivity = new Intent(getApplicationContext(), AuftragDetailsActivity.class);
                detailActivity.putExtra("id", kehrung.getId());
                startActivity(detailActivity);
            }
        });

    }

    // Gibt dem Menubutton seine Funktionalität
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        koordinator = new ListenKoordinator(this);
        liste = koordinator.erledigteKehrungen();
        adapter.notifyDataSetChanged(); // TODO funktioniert noch nicht
    }
}
