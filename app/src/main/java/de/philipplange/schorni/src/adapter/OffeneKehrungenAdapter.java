package de.philipplange.schorni.src.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.philipplange.schorni.R;
import de.philipplange.schorni.src.fragments.PageFragment;
import de.philipplange.schorni.src.models.Kehrung;

/**
 * Adapter für das Einfuegen der offenen Kehrungen in die Auftragslisten
 */

public class OffeneKehrungenAdapter extends ArrayAdapter<Kehrung> {
    public OffeneKehrungenAdapter(PageFragment context, ArrayList<Kehrung> kehrungen) {
        super(context.getContext(), 0, kehrungen);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Kehrung kehrung = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_offene_kehrung, parent, false);
        }
        // Lookup view for data population
        TextView tvAdresse = (TextView) convertView.findViewById(R.id.tvAdresse);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        // Populate the data into the template view using the data object
        if (kehrung != null) {
            tvAdresse.setText(kehrung.getAdresse());
        }
        if (kehrung != null) {
            tvName.setText(kehrung.getName());
        }

        // Return the completed view to render on screen
        return convertView;

    }
}
