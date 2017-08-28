package de.philipplange.schorni.src.activities;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;

import de.philipplange.schorni.R;
import de.philipplange.schorni.src.hilfsklassen.ListenKoordinator;
import de.philipplange.schorni.src.hilfsklassen.TextDateiGenerator;


public class ExportActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private Toolbar mToolbar;
    private NavigationView nvDrawer;

    private RadioGroup radioGroup;
    private RadioButton rbEinenTag, rbMehrereTage;
    private TextView tvVon, tvBis;
    private Button btnTagWaehlenVon, btnTagWaehlenBis, btnExportieren, btnLoeschen;

    private DateTime dateTimeVon, dateTimeBis;
    ListenKoordinator koordinator;

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
        btnLoeschen = (Button) findViewById(R.id.btnLoeschen);


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
        tvBis.setAlpha(0.5f);
        dateTimeVon = new DateTime().withTimeAtStartOfDay();
        dateTimeBis = new DateTime();
        tvVon.setText(dateTimeVon.toString("dd.MM.yyyy"));
        tvBis.setText(dateTimeBis.toString("dd.MM.yyyy"));


        btnTagWaehlenVon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStartDate(dateTimeVon);
            }
        });

        btnTagWaehlenBis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateEndDate(dateTimeBis);
            }
        });

        btnExportieren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exportieren();
            }
        });

        btnLoeschen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loeschen();
            }
        });
    }

    /**
     * erzeugt einen String der die Export-Textdatei darstellt und versendet sie mit Hilfe der Android Sharemoeglichkeit
     */
    private void exportieren() {
        if (rbEinenTag.isChecked()) {
            dateTimeBis = new DateTime(dateTimeVon.getMillis()).withTime(23, 59, 59 , 0);
        }

        koordinator = new ListenKoordinator(this);
        TextDateiGenerator textDateiGenerator = new TextDateiGenerator();
        String text = textDateiGenerator.erzeugeformatiertenTxt(koordinator.getErledigteAuftraegeVonBis(dateTimeVon.getMillis(), dateTimeBis.getMillis()));

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "hallo"));
    }


    private void loeschen() {
        koordinator = new ListenKoordinator(this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Aufträge wirklich löschen?");
        alertDialogBuilder.setMessage("Alle Aufträge aus dem gewählten Zeitraum werden vom Gerät gelöscht.");
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (rbEinenTag.isChecked()) {
                    dateTimeBis = new DateTime(dateTimeVon.getMillis()).withTime(23, 59, 59 , 0);
                }


                koordinator.loescheErledigteAuftraegeVonBis(dateTimeVon.getMillis(), dateTimeBis.getMillis());
                Toast.makeText(getApplicationContext(), "Autraege gelöscht.", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }

    private void updateStartDate(DateTime startDate) {

        DatePickerDialog pickerDialogVon = new DatePickerDialog(this, d1, startDate.getYear(), startDate.getMonthOfYear() - 1, startDate.getDayOfMonth());
        pickerDialogVon.show();


    }

    DatePickerDialog.OnDateSetListener d1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            dateTimeVon = new DateTime(year, monthOfYear + 1, dayOfMonth, 0, 0);
            Log.d("EXPORT", "Startdatum festgelegt auf: " + dateTimeVon.toString());
            tvVon.setText(dateTimeVon.toString("dd.MM.yyyy"));
            if (dateTimeVon.isAfter(dateTimeBis) && btnTagWaehlenBis.isEnabled()) {
                Toast.makeText(ExportActivity.this, "Datum muss vor dem EndDatum liegen.", Toast.LENGTH_SHORT).show();
                btnExportieren.setEnabled(false);
            }
            else {
                btnExportieren.setEnabled(true);
            }
        }
    };




    private void updateEndDate(DateTime startDate) {

        DatePickerDialog pickerDialog = new DatePickerDialog(this, d2, startDate.getYear(), startDate.getMonthOfYear() - 1, startDate.getDayOfMonth());
        pickerDialog.show();


    }

    DatePickerDialog.OnDateSetListener d2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

            dateTimeBis = new DateTime(year, monthOfYear + 1, dayOfMonth, 23, 59);
            Log.d("EXPORT", "Endatum festgelegt auf: " + dateTimeBis.toString());
            tvBis.setText(dateTimeBis.toString("dd.MM.yyyy"));
            if (dateTimeBis.isBefore(dateTimeVon)) {
                Toast.makeText(ExportActivity.this, "EndDatum muss hinter dem StartDatum liegen.", Toast.LENGTH_SHORT).show();
                btnExportieren.setEnabled(false);
                btnLoeschen.setEnabled(false);
            }
            else {
                btnExportieren.setEnabled(true);
                btnLoeschen.setEnabled(true);
            }
        }
    };


    /**
     * Ueberprueft, ob das Startdatum vor dem Enddatum liegt
     * @return
     */
    private boolean checkVonBis() {
       return dateTimeVon.isBefore(dateTimeBis);
    }




    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rbEinenTag:
                if (checked) {
                    btnTagWaehlenBis.setEnabled(false);
                    tvBis.setAlpha(0.5f);
                    btnExportieren.setEnabled(true);
                }
                break;
            case R.id.rbMehrereTage:
                if (checked) {
                    btnTagWaehlenBis.setEnabled(true);
                    tvBis.setAlpha(1f);
                    if (!checkVonBis()) {
                        btnExportieren.setEnabled(false);
                        Toast.makeText(ExportActivity.this, "StartDatum muss vor dem EndDatum liegen.", Toast.LENGTH_SHORT).show();
                    }
                    else
                        btnExportieren.setEnabled(true);
                }
                break;
        }
    }
}
