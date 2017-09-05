package de.philipplange.schorni.src.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import org.joda.time.DateTime;

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

    DateTime temp; // für Contextmenu benoetigt
    Kehrung tempKehrung;

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
                    case (R.id.nav_import):
                        Intent importActivity = new Intent(getApplicationContext(), ImportActivity.class);
                        startActivity(importActivity);
                        finish();
                        break;
                    case (R.id.nav_export):
                        Intent exportActivity = new Intent(getApplicationContext(), ExportActivity.class);
                        startActivity(exportActivity);
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

        // Contextmenu um die Zeit nachtraeglich zu aendern
        registerForContextMenu(listView);

    }

    // Gibt dem Menubutton seine Funktionalität
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }


    // Contextmenu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.lvErledigt) {
            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            tempKehrung = (Kehrung) lv.getItemAtPosition(acmi.position);
            menu.add("Zeitpunkt ändern");


        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        switch (item.getItemId()) {
            case 0:
                DateTime date = new DateTime(tempKehrung.getErledigt());
                DatePickerDialog pickerDialogVon = new DatePickerDialog(this, d, date.getYear(), date.getMonthOfYear() - 1, date.getDayOfMonth());
                pickerDialogVon.show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            DateTime date = new DateTime(year, monthOfYear + 1, dayOfMonth, 0, 0);
            tempKehrung.setErledigt(date.getMillis());
            koordinator.updateKehrung(tempKehrung);
            // Activity neustarten
            Intent intent = new Intent(getApplicationContext(), ErledigtActivity.class);
            startActivity(intent);
        }
    };


}
