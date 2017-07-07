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
        ArrayList<Long> liste = new ArrayList<>();
        //Cursor kehrungen = cupboard().withDatabase(db).query(Kehrung.class).withSelection("erledigt = ?", "NULL").getCursor(); // Gibt alle Kehrungen, die noch nicht abgeschlossen wurden
        Cursor kehrungen = cupboard().withDatabase(db).query(Kehrung.class).withSelection("erledigt isnull").getCursor(); // Holt alle Kehrungen aus der DB
        try {
            QueryResultIterable<Kehrung> itr = cupboard().withCursor(kehrungen).iterate(Kehrung.class);
            for (Kehrung kehrung : itr) {
                long id = kehrung.getTableId();
                if (!liste.contains(id))
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

    /**
     * Gibt alle Kehrungen einer uebergebenen Tabelle zurueck
     *
     * @param listID ID der Tabelle
     * @return Liste mit Kehrungen einer bestimmten Tabelle
     */
    public ArrayList<Kehrung> offeneKehrungen(long listID) {
        return (ArrayList<Kehrung>) cupboard().withDatabase(db).query(Kehrung.class).withSelection("erledigt isnull").withSelection("tableId = ?", String.valueOf(listID)).list();
    }

    public ArrayList<Kehrung> erledigteKehrungen() {
        // TODO Optimisation; nur Kehrungen die abgeschlossen sind aus der DB holen
        ArrayList<Kehrung> liste = new ArrayList<>();
        Cursor kehrungen = cupboard().withDatabase(db).query(Kehrung.class).getCursor(); // Holt alle Kehrungen aus der DB
        try {
            QueryResultIterable<Kehrung> itr = cupboard().withCursor(kehrungen).iterate(Kehrung.class);
            for (Kehrung kehrung : itr) {
                if (kehrung.getErledigt() != null)
                    liste.add(kehrung);
            }
        } finally {
            kehrungen.close();
        }
        return liste;
    }

    /**
     * Gibt die Kehrung mit der uebergebenen ID aus der Datenbank zurueck.
     * @param id ID der Kehrung, die aus der Datenbank geholt werden soll.
     * @return Kehrung mit der uebergebenen ID.
     */
    public Kehrung getKehrung(long id) {
        return cupboard().withDatabase(db).get(Kehrung.class, id);
    }

    /**
     * Ersetzt eine Kehrung mit der uebergebenen Kehrung
     *
     * @param kehrung Kehrung die mit der gleichen id ueberschrieben werden soll.
     */
    public void updateKehrung(Kehrung kehrung) {
        cupboard().withDatabase(db).put(kehrung);
    }
}
