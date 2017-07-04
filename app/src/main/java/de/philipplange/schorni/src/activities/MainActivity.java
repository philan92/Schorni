package de.philipplange.schorni.src.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;

import de.philipplange.schorni.R;
import de.philipplange.schorni.src.adapter.SampleFragmentPagerAdapter;
import de.philipplange.schorni.src.hilfsklassen.DatenbankTestdaten;
import de.philipplange.schorni.src.hilfsklassen.ListenKoordinator;

public class MainActivity extends AppCompatActivity {


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private Toolbar mToolbar;
    NavigationView nvDrawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // erzeugt Testdaten zum Debuggen
        DatenbankTestdaten.erzeugeDBTestDaten(this);

        // Aktive Listen in die Shared Preferences schreiben
        // TODO kann vermutlich gelöscht werden
        ListenKoordinator koordinator = new ListenKoordinator(this);
        ArrayList<Long> liste = koordinator.getAktiveListIDs();


        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.assignments);

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
                        break;
                }
                return true;
            }
        });


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(), MainActivity.this, liste));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);



        System.out.println();

    }


    // Gibt dem Menubutton seine Funktionalität
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }


}











