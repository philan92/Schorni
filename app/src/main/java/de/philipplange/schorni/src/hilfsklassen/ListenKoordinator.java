package de.philipplange.schorni.src.hilfsklassen;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;

import de.philipplange.schorni.src.models.Kehrung;
import nl.qbusict.cupboard.QueryResultIterable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;


public class ListenKoordinator {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

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
            Collections.sort(liste);
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
        ArrayList<Long> liste = new ArrayList<>();
        Cursor kehrungen = cupboard().withDatabase(db).query(Kehrung.class).withSelection("erledigt IS NULL").getCursor();
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
        ArrayList<Kehrung> liste = new ArrayList<>();
        Cursor kehrungen = cupboard().withDatabase(db).query(Kehrung.class).withSelection("erledigt IS NULL AND tableid = ?", String.valueOf(listID)).getCursor();
        try {
            QueryResultIterable<Kehrung> itr = cupboard().withCursor(kehrungen).iterate(Kehrung.class);
            for (Kehrung kehrung : itr) {
                //if (kehrung.getTableId() == listID)
                    liste.add(kehrung);
            }
        } finally {
            kehrungen.close();
        }
        return liste;

    }

    public ArrayList<Kehrung> erledigteKehrungen() {

        ArrayList<Kehrung> liste = new ArrayList<>();
        Cursor kehrungen = cupboard().withDatabase(db).query(Kehrung.class).withSelection("erledigt IS NOT NULL ORDER BY erledigt DESC").getCursor();
        try {
            QueryResultIterable<Kehrung> itr = cupboard().withCursor(kehrungen).iterate(Kehrung.class);
            for (Kehrung kehrung : itr) {
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
     * Packt eine neue Auftragsliste in die DB und vergibt dieser eine korrekte TabellenID
     *
     * @param kehrungen Liste der zu importierenden Kehrungen
     * @return erfog des Imports
     */
    public boolean importKehrungen(ArrayList<Kehrung> kehrungen) {
        long neueTableID = getNeueListenID();
        for (Kehrung kehrung : kehrungen) {
            kehrung.setTableId(neueTableID);
            cupboard().withDatabase(db).put(kehrung);
        }
        return false;
    }

    /**
     * Gibt eine neue ListenID zurueck, die um 1 hoeher ist als die bisher vergebenen.
     *
     * @return neue verfuegbare ListenID
     */
    private long getNeueListenID() {
        long max = 0;

        Cursor kehrungen = cupboard().withDatabase(db).query(Kehrung.class).getCursor(); // Holt alle Kehrungen aus der DB
        try {
            QueryResultIterable<Kehrung> itr = cupboard().withCursor(kehrungen).iterate(Kehrung.class);
            for (Kehrung kehrung : itr) {
                if (kehrung.getTableId() > max)
                    max = kehrung.getTableId();
            }
        } finally {
            kehrungen.close();
        }

        return max + 1;
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
