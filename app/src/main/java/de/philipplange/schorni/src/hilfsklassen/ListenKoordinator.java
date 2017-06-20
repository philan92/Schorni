package de.philipplange.schorni.src.hilfsklassen;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import de.philipplange.schorni.src.models.Kehrung;
import nl.qbusict.cupboard.QueryResultIterable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;


public class ListenKoordinator {

    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    public ListenKoordinator(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }


    /**
     * Gibt eine Liste mit den ListIDs, in denen es noch nicht abgeschlossene Auftraege gibt
     *
     * @return Liste mit unique ListenIDs
     */
    public ArrayList<Long> getAktiveListIDs() {
        // TODO Optimisation; nur Kehrungen die noch nicht abgeschlossen sind aus der DB holen
        ArrayList<Long> liste = new ArrayList<>();
        //Cursor kehrungen = cupboard().withDatabase(db).query(Kehrung.class).withSelection("erledigt = ?", "NULL").getCursor(); // Gibt alle Kehrungen, die noch nicht abgeschlossen wurden
        Cursor kehrungen = cupboard().withDatabase(db).query(Kehrung.class).getCursor(); // Holt alle Kehrungen aus der DB
        try {
            QueryResultIterable<Kehrung> itr = cupboard().withCursor(kehrungen).iterate(Kehrung.class);
            for (Kehrung kehrung : itr) {
                long id = kehrung.getTableId();
                if (kehrung.getErledigt() == null && !liste.contains(id))
                    liste.add(id);
            }
        } finally {
            kehrungen.close();
        }
        return liste;
    }

    /**
     * Gibt eine Liste mit IDs aller offenen Kehrungen zurück
     *
     * @return Liste aller offenen Kehrungen
     */
    public ArrayList<Long> offeneKehrungen() {
        // TODO Optimisation; nur Kehrungen die noch nicht abgeschlossen sind aus der DB holen
        ArrayList<Long> liste = new ArrayList<>();
        Cursor kehrungen = cupboard().withDatabase(db).query(Kehrung.class).getCursor(); // Holt alle Kehrungen aus der DB
        try {
            QueryResultIterable<Kehrung> itr = cupboard().withCursor(kehrungen).iterate(Kehrung.class);
            for (Kehrung kehrung : itr) {
                long id = kehrung.getId();
                if (kehrung.getErledigt() == null)
                    liste.add(id);
            }
        } finally {
            kehrungen.close();
        }
        return liste;
    }

    public ArrayList<Kehrung> offeneKehrungen(long listID) {
        // TODO Optimisation; nur Kehrungen die noch nicht abgeschlossen sind aus der DB holen
        ArrayList<Kehrung> liste = new ArrayList<>();
        Cursor kehrungen = cupboard().withDatabase(db).query(Kehrung.class).getCursor(); // Holt alle Kehrungen aus der DB
        try {
            QueryResultIterable<Kehrung> itr = cupboard().withCursor(kehrungen).iterate(Kehrung.class);
            for (Kehrung kehrung : itr) {
                if (kehrung.getErledigt() == null && kehrung.getTableId() == listID)
                    liste.add(kehrung);
            }
        } finally {
            kehrungen.close();
        }
        return liste;
    }
}
