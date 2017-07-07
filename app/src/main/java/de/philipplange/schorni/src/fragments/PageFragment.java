package de.philipplange.schorni.src.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import de.philipplange.schorni.R;
import de.philipplange.schorni.src.activities.AuftragDetailsActivity;
import de.philipplange.schorni.src.adapter.OffeneKehrungenAdapter;
import de.philipplange.schorni.src.hilfsklassen.ListenKoordinator;
import de.philipplange.schorni.src.models.Kehrung;


/**
 * Repraesentiert eine Liste im Auftragsbildschirm
 */

public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String LIST_ID = "LIST_ID";

    private int mPage; //Anzahl der benutzten Fragments
    private long mlistID; //ListID die vom Fragment angezeigt wird

    OffeneKehrungenAdapter adapter;

    public static PageFragment newInstance(int page, long listID) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putLong(LIST_ID, listID);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        mlistID = getArguments().getLong(LIST_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);

        // TODO Die Kehrungen in die richtigen Listen aus der DB einfügen

        ArrayList<Kehrung> kehrungen;

        ListenKoordinator listenKoordinator = new ListenKoordinator(getContext());
        kehrungen = listenKoordinator.offeneKehrungen(mlistID);

        // Create the adapter to convert the array to views
        adapter = new OffeneKehrungenAdapter(this, kehrungen);
        // Attach the adapter to a ListView
        ListView listView = (ListView) view;
        listView.setAdapter(adapter);

        // Die Listeneinträge werden clickbar gemacht
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Kehrung kehrung = (Kehrung) adapterView.getItemAtPosition(i);
                Intent detailActivity = new Intent(getActivity(), AuftragDetailsActivity.class);
                detailActivity.putExtra("id", kehrung.getId());
                startActivity(detailActivity);
            }
        });

        //TextView textView = (TextView) view;
        //textView.setText("Fragment #" + mPage);
        return view;
    }
}