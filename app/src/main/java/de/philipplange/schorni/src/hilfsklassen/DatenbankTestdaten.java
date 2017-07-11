package de.philipplange.schorni.src.hilfsklassen;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import de.philipplange.schorni.src.models.Kehrung;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by Philipp on 20.06.2017.
 */

public class DatenbankTestdaten {

    public static void erzeugeDBTestDaten(Context context) {

        // Damit hat man Zugriff auf die Datenbank
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // zu Testzwecken bei jedem Start DB loeschen
        cupboard().withDatabase(db).delete(Kehrung.class, null);

        Kehrung kehrung = new Kehrung("Strasse 1, 123456 Fummelsdorf", "Neele Rother", "123456789", "1xSKF/2", "KamO 2008 Umluft", "Das ist eine Bemerkung.", false);
        kehrung.setTableId(1);
        //kehrung.setErledigt(new Date().getTime());
        cupboard().withDatabase(db).put(kehrung);

        kehrung = new Kehrung("Strasse 2, 123456 Fummelsdorf", "Neele Rother", "123456789", "1xSKF/2", "KamO 2008 Umluft", "", true);
        kehrung.setTableId(1);
        //kehrung.setErledigt(new Date().getTime());
        cupboard().withDatabase(db).put(kehrung);

        kehrung = new Kehrung("Strasse 3, 123456 Fummelsdorf", "Neele Rother", "123456789", "1xSKF/2", "KamO 2008 Umluft", "", false);
        kehrung.setTableId(235);
        //kehrung.setErledigt(new Date().getTime());
        cupboard().withDatabase(db).put(kehrung);

        kehrung = new Kehrung("Strasse 4, 123456 Fummelsdorf", "Neele Rother", "123456789", "1xSKF/2", "KamO 2008 Umluft", "", false);
        kehrung.setTableId(2);
        //kehrung.setErledigt(new Date().getTime());
        cupboard().withDatabase(db).put(kehrung);

        kehrung = new Kehrung("Strasse 5, 123456 Fummelsdorf", "Neele Rother", "123456789", "1xSKF/2", "KamO 2008 Umluft", "", false);
        kehrung.setTableId(2);
        //kehrung.setErledigt(new Date().getTime());
        cupboard().withDatabase(db).put(kehrung);

        kehrung = new Kehrung("Strasse 6, 123456 Fummelsdorf", "Philipp Lange", "123456789", "1xSKF/2", "KamO 2008 Umluft", "", false);
        kehrung.setTableId(1);
        //kehrung.setErledigt(new Date().getTime());
        cupboard().withDatabase(db).put(kehrung);
/*
        kehrung = new Kehrung("Strasse 7, 123456 Fummelsdorf", "Philipp Lange", "123456789", "1xSKF/2", "KamO 2008 Umluft", "", false);
        kehrung.setTableId(1);
        //kehrung.setErledigt(new Date().getTime());
        cupboard().withDatabase(db).put(kehrung);

        kehrung = new Kehrung("Strasse 8, 123456 Fummelsdorf", "Philipp Lange", "123456789", "1xSKF/2", "KamO 2008 Umluft", "", false);
        kehrung.setTableId(1);
        //kehrung.setErledigt(new Date().getTime());
        cupboard().withDatabase(db).put(kehrung);

        kehrung = new Kehrung("Strasse 20, 123456 Fummelsdorf", "Neele Rother", "123456789", "1xSKF/2", "KamO 2008 Umluft", "", false);
        kehrung.setTableId(1);
        //kehrung.setErledigt(new Date().getTime());
        cupboard().withDatabase(db).put(kehrung);

        kehrung = new Kehrung("Strasse 30, 123456 Fummelsdorf", "Neele Rother", "123456789", "1xSKF/2", "KamO 2008 Umluft", "", false);
        kehrung.setTableId(1);
        //kehrung.setErledigt(new Date().getTime());
        cupboard().withDatabase(db).put(kehrung);

        kehrung = new Kehrung("Strasse 40, 123456 Fummelsdorf", "Neele Rother", "123456789", "1xSKF/2", "KamO 2008 Umluft", "", false);
        kehrung.setTableId(1);
        //kehrung.setErledigt(new Date().getTime());
        cupboard().withDatabase(db).put(kehrung);

        kehrung = new Kehrung("Strasse 50, 123456 Fummelsdorf", "Neele Rother", "123456789", "1xSKF/2", "KamO 2008 Umluft", "", false);
        kehrung.setTableId(1);
        //kehrung.setErledigt(new Date().getTime());
        cupboard().withDatabase(db).put(kehrung);

        kehrung = new Kehrung("Strasse 60, 123456 Fummelsdorf", "Philipp Lange", "123456789", "1xSKF/2", "KamO 2008 Umluft", "", false);
        kehrung.setTableId(1);
        //kehrung.setErledigt(new Date().getTime());
        cupboard().withDatabase(db).put(kehrung);

        kehrung = new Kehrung("Strasse 70, 123456 Fummelsdorf", "Philipp Lange", "123456789", "1xSKF/2", "KamO 2008 Umluft", "", false);
        kehrung.setTableId(1);
        //kehrung.setErledigt(new Date().getTime());
        cupboard().withDatabase(db).put(kehrung);

        kehrung = new Kehrung("Strasse 80, 123456 Fummelsdorf", "Philipp Lange", "123456789", "1xSKF/2", "KamO 2008 Umluft", "", false);
        kehrung.setTableId(1);
        //kehrung.setErledigt(new Date().getTime());
        cupboard().withDatabase(db).put(kehrung);

        kehrung = new Kehrung("Strasse 3, 123456 Fummelsdorf", "Philipp Lange", "123456789", "1xSKF/2", "KamO 2008 Umluft", "", false);
        kehrung.setTableId(2);
        kehrung.setErledigt(new Date().getTime());
        cupboard().withDatabase(db).put(kehrung);

        kehrung = new Kehrung("Strasse 4, 123456 Fummelsdorf", "Philipp Lange", "123456789", "1xSKF/2", "KamO 2008 Umluft", "", false);
        kehrung.setTableId(3);
        cupboard().withDatabase(db).put(kehrung);

        kehrung = new Kehrung("Strasse 3, 123456 Fummelsdorf", "Philipp Lange", "123456789", "1xSKF/2", "KamO 2008 Umluft", "", false);
        kehrung.setTableId(20);
        cupboard().withDatabase(db).put(kehrung);

        //Kehrung k = cupboard().withDatabase(db).get(Kehrung.class, 2);
        //Log.d("TESTING", k.toString());

*/
    }
}
