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
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.joda.time.DateTime;

import de.philipplange.schorni.R;

public class ExportActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private Toolbar mToolbar;
    private NavigationView nvDrawer;

    private RadioGroup radioGroup;
    private RadioButton rbEinenTag, rbMehrereTage;
    private TextView tvVon, tvBis;
    private Button btnTagWaehlenVon, btnTagWaehlenBis, btnExportieren;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);

        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        rbEinenTag = (RadioButton) findViewById(R.id.rbEinenTag);
        rbMehrereTage = (RadioButton) findViewById(R.id.rbMehrereTage);

        tvVon = (TextView) findViewById(R.id.tvVon);
        tvBis = (TextView) findViewById(R.id.tvBis);

        btnTagWaehlenVon = (Button) findViewById(R.id.btnTagWaehlenVon);
        btnTagWaehlenBis = (Button) findViewById(R.id.btnTagWaehlenBis);
        btnExportieren = (Button) findViewById(R.id.btnExportieren);



        // Sidemenu vorbereitung TODO in Funktion auslagern
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Export");

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

        // Standardeinstellungen vornehmen
        btnTagWaehlenBis.setEnabled(false);
        tvVon.setText(new DateTime().toString("dd.MM.yyyy"));
        tvBis.setText(new DateTime().toString("dd.MM.yyyy"));




    }

    // Gibt dem Menubutton seine Funktionalität
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rbEinenTag:
                if (checked)
                    btnTagWaehlenBis.setEnabled(false);
                    break;
            case R.id.rbMehrereTage:
                if (checked)
                    btnTagWaehlenBis.setEnabled(true);
                    break;
        }
    }
}
