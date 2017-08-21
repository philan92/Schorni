package de.philipplange.schorni.src.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.ArrayList;

import de.philipplange.schorni.R;
import de.philipplange.schorni.src.models.Kehrung;



public class ErledigteKehrungenAdapter extends ArrayAdapter<Kehrung> {
    public ErledigteKehrungenAdapter(Context context, ArrayList<Kehrung> kehrungen) {
        super(context, 0, kehrungen);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Kehrung kehrung = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_erledigte_kehrung, parent, false);
        }
        // Lookup view for data population
        TextView tvAdresse = (TextView) convertView.findViewById(R.id.tvAdresse);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvZeit = (TextView) convertView.findViewById(R.id.tvZeit);
        ImageView ivKassiert = (ImageView) convertView.findViewById(R.id.ivKassiert);
        // Populate the data into the template view using the data object
        if (kehrung != null) {
            tvAdresse.setText(kehrung.getAdresse());
            tvName.setText(kehrung.getName());
            tvZeit.setText(showDateFromLong(kehrung.getErledigt())); // Zeit vom Abschluss des Auftrags
            tvZeit.setAlpha(0.5f);
            if (!kehrung.isKassiert()) {
                ivKassiert.setImageResource(R.mipmap.ic_money_off_white_24dp);
                //ivKassiert.setColorFilter(Color.parseColor("#EF5350"));
                ivKassiert.setBackgroundColor(Color.parseColor("#EF5350"));
            } else {
                ivKassiert.setImageResource(R.mipmap.ic_attach_money_white_24dp);
                //ivKassiert.setColorFilter(Color.parseColor("#43A047"));
                ivKassiert.setBackgroundColor(Color.parseColor("#43A047"));
            }
        }

        // Return the completed view to render on screen
        return convertView;
    }

    /**
     * Berechnet aus dem long das korrekte Datum und gibt es als String zurueck
     *
     * @param longTime
     * @return
     */
    private String showDateFromLong(long longTime) {
        DateTime dateTime = new DateTime(longTime);
        String string = dateTime.getDayOfMonth() + "." + dateTime.getMonthOfYear() + "." + dateTime.getYear();
        return string;
    }
}
