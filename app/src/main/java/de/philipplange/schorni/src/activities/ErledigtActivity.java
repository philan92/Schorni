package de.philipplange.schorni.src.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erledigt);

        // Liste mit erledigten Kehrungen erstellen
        ListenKoordinator koordinator = new ListenKoordinator(this);
        ArrayList<Kehrung> liste = koordinator.erledigteKehrungen();


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
                        mDrawerLayout.closeDrawers();
                        break;
                    case (R.id.nav_erledigt):
                        Intent erledigtActivity = new Intent(getApplicationContext(), ErledigtActivity.class);
                        startActivity(erledigtActivity);
                        mDrawerLayout.closeDrawers();
                        break;
                }
                return true;
            }
        });

        // befüllt die Liste mit den erledigten Aufträgen
        ErledigteKehrungenAdapter adapter = new ErledigteKehrungenAdapter(this, liste);
        listView = (ListView) findViewById(R.id.lvErledigt);
        listView.setAdapter(adapter);

    }

    // Gibt dem Menubutton seine Funktionalität
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}
