package com.example.dilkidias.gramatica;

import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;
import com.example.dilkidias.gramatica.Data.DataContract;

import static android.R.attr.id;


public class VerbFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private VerbCursorAdapter mCursorAdapter;
    private static final int VERB_LOADER = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_verb, container, false);

        mCursorAdapter = new VerbCursorAdapter(getActivity(), null);
        ListView listVerb = (ListView) rootView.findViewById(R.id.list_verb);
        listVerb.setAdapter(mCursorAdapter);

        // set up on item click
        listVerb.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewVerb(id);
            }
        });

        listVerb.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {
                PopupMenu popup = new PopupMenu(getContext(), view);
                popup.getMenuInflater().inflate(R.menu.popup_menu,
                        popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.viewVerb:
                                viewVerb(id);
                                break;
                            case R.id.editVerb:
                                Intent intent = new Intent(getActivity(), EditorActivity.class);
                                Uri currentPetUri = ContentUris.withAppendedId(DataContract.DataEntry.VERBS_CONTENT_URI, id);
                                intent.setData(currentPetUri);
                                startActivity(intent);
                                break;
                            case R.id.deleteVerb:
                                showDeleteConfirmationDialog(ContentUris.withAppendedId(DataContract.DataEntry.VERBS_CONTENT_URI, id));
                                break;
                        }
                        return true;
                    }
                });
                return true;
            }
        });

        getActivity().getSupportLoaderManager().initLoader(VERB_LOADER, null, this);

        return rootView;
    }

    private void deleteVerb(Uri uri) {

        // Otherwise this is an EXISTING pet, so update the pet with content URI: mCurrentPetUri
        // and pass in the new ContentValues. Pass in null for the selection and selection args
        // because mCurrentPetUri will already identify the correct row in the database that
        // we want to modify.
        int rowsAffected = getActivity().getContentResolver().delete(uri, null, null);

        // Show a toast message depending on whether or not the update was successful.
        if (rowsAffected == 0) {

            // If no rows were affected, then there was an error with the update.
            Toast.makeText(getActivity(), R.string.delete_verb_failed,
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the update was successful and we can display a toast.
            Toast.makeText(getActivity(), R.string.delete_verb_successfully,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void showDeleteConfirmationDialog(final Uri uri) {

        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.delete_verb);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked the "Delete" button, so delete the pet.
                        deleteVerb(uri);
                    }
                    });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void viewVerb(Long id) {

        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        Uri currentPetUri = ContentUris.withAppendedId(DataContract.DataEntry.VERBS_CONTENT_URI, id);
        intent.setData(currentPetUri);
        startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Define a projection that specifies the columns from the table we care about.
        String[] projection = {"*"};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(getActivity(), // Parent activity context
                DataContract.DataEntry.VERBS_CONTENT_URI, // Provider content URI to query
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
