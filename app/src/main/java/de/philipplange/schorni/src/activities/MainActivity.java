package de.philipplange.schorni.src.activities;

import android.os.Bundle;
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
import de.philipplange.schorni.src.hilfsklassen.ListenKoordinator;

public class MainActivity extends AppCompatActivity {


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        /////////////////////////////////////////////////////////TESTBEREICH
        // Damit hat man Zugriff auf die Datenbank
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // zu Testzwecken bei jedem Start DB loeschen
        cupboard().withDatabase(db).delete(Kehrung.class, null);

        Kehrung kehrung = new Kehrung("Strasse 1, 123456 Fummelsdorf", "Hidolf Atler", "123456789", "1xSKF/2", "KamO 2008 Umluft", "", false);
        kehrung.setTableId(1);
        //kehrung.setErledigt(new Date().getTime());
        cupboard().withDatabase(db).put(kehrung);

        kehrung = new Kehrung("Strasse 2, 123456 Fummelsdorf", "Philipp Lange", "123456789", "1xSKF/2", "KamO 2008 Umluft", "", false);
        kehrung.setTableId(1);
        //kehrung.setErledigt(new Date().getTime());
        cupboard().withDatabase(db).put(kehrung);

        kehrung = new Kehrung("Strasse 3, 123456 Fummelsdorf", "Philipp Lange", "123456789", "1xSKF/2", "KamO 2008 Umluft", "", false);
        kehrung.setTableId(2);
        kehrung.setErledigt(new Date().getTime());
        cupboard().withDatabase(db).put(kehrung);

        kehrung = new Kehrung("Strasse 3, 123456 Fummelsdorf", "Philipp Lange", "123456789", "1xSKF/2", "KamO 2008 Umluft", "", false);
        kehrung.setTableId(3);
        cupboard().withDatabase(db).put(kehrung);

        //Kehrung k = cupboard().withDatabase(db).get(Kehrung.class, 2);
        //Log.d("TESTING", k.toString());


        /////////////////////////////////////////////////////////TESTBEREICH ENDE
*/
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


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(), MainActivity.this, liste));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}












