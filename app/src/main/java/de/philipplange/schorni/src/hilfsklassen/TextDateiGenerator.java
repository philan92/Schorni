package de.philipplange.schorni.src.hilfsklassen;

import android.util.Log;

import org.joda.time.DateTime;

import java.util.ArrayList;

import de.philipplange.schorni.src.models.Kehrung;

/**
 * Created by Philipp on 28.08.2017.
 */

public class TextDateiGenerator {
    public String erzeugeformatiertenTxt(ArrayList<Kehrung> liste) {
        String s = "";
        for (Kehrung k : liste) {
            s += "# " + String.valueOf(k.getTableId()) +" | "+ new DateTime(k.getErledigt()).toString("dd.MM.yyyy") + " | "+ k.getAdresse() + " // " + k.getBemerkungen() + "\n";
        }
        Log.d("TEXTDATEI", s);
        return s;
    }
}
