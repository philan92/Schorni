package de.philipplange.schorni.src.hilfsklassen;

import de.philipplange.schorni.src.models.Kehrung;

/**
 * Created by Philipp on 16.08.2017.
 * Erstellt aus einem String im Format Adresse;Telefon;Name;Arbeitsinfo;Arbeitskürzel ein Kehrungsobjekt
 */

public class CSVParser {
    public static Kehrung erstelleKehrungAusString(String zeile) {
        String[] werte = zeile.split(";");
        if (werte.length < 5 || werte.length > 5) {
            return null;
        }

        // ueberfluessige Leerzeichen entfernen
        for (int i = 0; i < werte.length; i++) {
            werte[i] = werte[i].trim();
        }
        Kehrung kehrung = new Kehrung(werte[0], werte[2], werte[1], werte[4], werte[3], "", false);
        if (!kehrung.getName().isEmpty() && !kehrung.getAdresse().isEmpty())
            return kehrung;
        else
            return null;
    }
}
