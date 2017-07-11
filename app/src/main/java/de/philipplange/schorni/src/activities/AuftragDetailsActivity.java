package de.philipplange.schorni.src.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import de.philipplange.schorni.R;
import de.philipplange.schorni.src.hilfsklassen.ListenKoordinator;
import de.philipplange.schorni.src.models.Kehrung;

public class AuftragDetailsActivity extends AppCompatActivity {

    ListenKoordinator koordinator;
    Kehrung kehrung;

    TextView tvAuftragsID, tvTableID, tvName, tvAdresse, tvTelefon, tvKuerzel, tvInfo;
    EditText etNotizen;
    CheckBox cbKassiert;
    Button btnAbgeschlossen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auftrag_detail);
        setTitle("Auftragsdetails");

        tvAuftragsID = (TextView) findViewById(R.id.tvAuftragsID);
        tvTableID = (TextView) findViewById(R.id.tvTableID);
        tvName = (TextView) findViewById(R.id.tvName);
        tvAdresse = (TextView) findViewById(R.id.tvAdresse);
        tvTelefon = (TextView) findViewById(R.id.tvTelefon);
        tvKuerzel = (TextView) findViewById(R.id.tvKuerzel);
        tvInfo = (TextView) findViewById(R.id.tvInfo);
        etNotizen = (EditText) findViewById(R.id.etNotizen);
        cbKassiert = (CheckBox) findViewById(R.id.cbKassiert);
        btnAbgeschlossen = (Button) findViewById(R.id.btnAbschliessen);

        koordinator = new ListenKoordinator(this);

        Long id;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                id = null;
            } else {
                id = extras.getLong("id");
            }
        } else {
            id = (Long) savedInstanceState.getSerializable("id");
        }

        kehrung = koordinator.getKehrung(id);


        // Anzeigen mit Daten füllen
        tvAuftragsID.setText("# " + String.valueOf(kehrung.getId()));
        tvTableID.setText("# " + String.valueOf(kehrung.getTableId()));
        tvName.setText(kehrung.getName());
        tvAdresse.setText(kehrung.getAdresse());
        tvTelefon.setText(kehrung.getFon());
        tvKuerzel.setText(kehrung.getKuerzel());
        tvInfo.setText(kehrung.getInfo());

        etNotizen.setText(kehrung.getBemerkungen());
        cbKassiert.setChecked(kehrung.isKassiert());

        btnAbgeschlossen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kehrung.setErledigt(999L); // TODO muss durch Timestamp ersetzt werden
                koordinator.updateKehrung(kehrung);
                Toast.makeText(AuftragDetailsActivity.this, "Auftrag erledigt", Toast.LENGTH_SHORT).show();
                Intent auftraegeActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(auftraegeActivity);
                finish();
            }
        });
    }
}
