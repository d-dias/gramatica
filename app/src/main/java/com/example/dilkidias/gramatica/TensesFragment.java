package com.example.dilkidias.gramatica;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dilkidias.gramatica.Data.DataContract;

public class TensesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private VerbCursorAdapter mCursorAdapter;
    private static final int VERB_LOADER = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_verb, container, false);

        mCursorAdapter = new VerbCursorAdapter(getActivity(), null);
        final ListView listVerb = (ListView) rootView.findViewById(R.id.list_verb);
        listVerb.setAdapter(mCursorAdapter);

        getActivity().getSupportLoaderManager().initLoader(VERB_LOADER, null, this);

        // set up on item click
        listVerb.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), TensesDetailsViewActivity.class);
                Uri currentPetUri = ContentUris.withAppendedId(DataContract.DataEntry.CONTENT_URI, id);
                intent.setData(currentPetUri);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Define a projection that specifies the columns from the table we care about.
        String[] projection = {"*"};


        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(getActivity(), // Parent activity context
                DataContract.DataEntry.CONTENT_URI, // Provider content URI to query
                projection, null, null, null);// Columns to include in the resulting Cursor
        // No selection clause
        // No selection arguments
        // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        loader.abandon();
        mCursorAdapter.swapCursor(null);
    }
}// Required empty public constructor
