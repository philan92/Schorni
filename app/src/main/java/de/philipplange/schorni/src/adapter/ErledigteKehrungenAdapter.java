package de.philipplange.schorni.src.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.philipplange.schorni.R;
import de.philipplange.schorni.src.models.Kehrung;

/**
 * Created by Philipp on 04.07.2017.
 */

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
        TextView tvKassiert = (TextView) convertView.findViewById(R.id.tvKassiert);
        // Populate the data into the template view using the data object
        if (kehrung != null) {
            tvAdresse.setText(kehrung.getAdresse());
            tvName.setText(kehrung.getName());
            tvZeit.setText(String.valueOf(kehrung.getErledigt())); // TODO In lesabres Datum ändern
            tvKassiert.setText("Kassiert: " + kehrung.isKassiert());
        }

        // Return the completed view to render on screen
        return convertView;
    }
}
