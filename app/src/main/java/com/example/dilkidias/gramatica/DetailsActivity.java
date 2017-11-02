package com.example.dilkidias.gramatica;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dilkidias.gramatica.Data.DataContract;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by DILKI DIAS on 04-Jun-17.
 */
public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, DetailsAdapter.ClickListener {

    private Toast toast = null;
    private RecyclerView recyclerView = null;
    /**
     * Content URI for the existing pet (null if it's a new pet)
     */
    private Uri mCurrentVerbUri;

    /**
     * Identifier for the pet data loader
     */
    private static final int EXISTING_PET_LOADER = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_new);

        Intent intent = getIntent();
        mCurrentVerbUri = intent.getData();

        getLoaderManager().initLoader(EXISTING_PET_LOADER, null, this);
    }

    private void showDeleteConfirmationDialog() {

        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_verb);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                // User clicked the "Delete" button, so delete the pet.
                deleteVerb();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Perform the deletion of the pet in the database.
     */
    private void deleteVerb() {

        // Otherwise this is an EXISTING pet, so update the pet with content URI: mCurrentPetUri
        // and pass in the new ContentValues. Pass in null for the selection and selection args
        // because mCurrentPetUri will already identify the correct row in the database that
        // we want to modify.
        int rowsAffected = getContentResolver().delete(mCurrentVerbUri, null, null);

        // Show a toast message depending on whether or not the update was successful.
        if (rowsAffected == 0) {

            // If no rows were affected, then there was an error with the update.
            toastView(getString(R.string.delete_verb_failed));
        } else {
            // Otherwise, the update was successful and we can display a toast.
            toastView(getString(R.string.delete_verb_successfully));
        }

        // Close the activity
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_edit:
                Intent intent = new Intent(DetailsActivity.this, EditorActivity.class);
                intent.setData(mCurrentVerbUri);
                startActivity(intent);
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Show a dialog that notifies the user going to delete the pet
                showDeleteConfirmationDialog();
                return true;

            case R.id.action_home:
                startActivity(new Intent(DetailsActivity.this, MainActivity.class));
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Define a projection that specifies the columns from the table we care about.
        String[] projection = {"*"};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this, // Parent activity context
                mCurrentVerbUri, // Provider content URI to query
                projection, null, null, null);// Columns to include in the resulting Cursor
        // No selection clause
        // No selection arguments
        // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if (cursor.moveToFirst()) {
            if (cursor.moveToFirst()) {
                // Find the columns of pet attributes that we're interested in
                int porColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_VERB_POR);
                int engColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_VERB_ENG);
                int presente1ColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_PRESENTE_1);
                int presente2ColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_PRESENTE_2);
                int presente3ColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_PRESENTE_3);
                int presente4ColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_PRESENTE_4);
                int presente5ColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_PRESENTE_5);
                int pps1ColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_P_P_S_1);
                int pps2ColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_P_P_S_2);
                int pps3ColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_P_P_S_3);
                int pps4ColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_P_P_S_4);
                int pps5ColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_P_P_S_5);
                int pis1ColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_P_I_S_1);
                int pis2ColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_P_I_S_2);
                int pis3ColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_P_I_S_3);
                int pis4ColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_P_I_S_4);
                int pis5ColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_P_I_S_5);
                int futuro1ColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_FUTURO_1);
                int futuro2ColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_FUTURO_2);
                int futuro3ColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_FUTURO_3);
                int futuro4ColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_FUTURO_4);
                int futuro5ColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_FUTURO_5);
                int pmqpc1ColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_P_M_Q_P_C_1);
                int pmqpc2ColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_P_M_Q_P_C_2);
                int pmqpc3ColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_P_M_Q_P_C_3);
                int pmqpc4ColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_P_M_Q_P_C_4);
                int pmqpc5ColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_P_M_Q_P_C_5);
                int ppc1ColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_P_P_C_1);
                int ppc2ColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_P_P_C_2);
                int ppc3ColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_P_P_C_3);
                int ppc4ColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_P_P_C_4);
                int ppc5ColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_P_P_C_5);

                // Extract out the value from the Cursor for the given column index
                String mPorVerb = cursor.getString(porColumnIndex);
                String engVerb = cursor.getString(engColumnIndex);
                String presente1Verb = cursor.getString(presente1ColumnIndex);
                String presente2Verb = cursor.getString(presente2ColumnIndex);
                String presente3Verb = cursor.getString(presente3ColumnIndex);
                String presente4Verb = cursor.getString(presente4ColumnIndex);
                String presente5Verb = cursor.getString(presente5ColumnIndex);
                String pps1Verb = cursor.getString(pps1ColumnIndex);
                String pps2Verb = cursor.getString(pps2ColumnIndex);
                String pps3Verb = cursor.getString(pps3ColumnIndex);
                String pps4Verb = cursor.getString(pps4ColumnIndex);
                String pps5Verb = cursor.getString(pps5ColumnIndex);
                String pis1Verb = cursor.getString(pis1ColumnIndex);
                String pis2Verb = cursor.getString(pis2ColumnIndex);
                String pis3Verb = cursor.getString(pis3ColumnIndex);
                String pis4Verb = cursor.getString(pis4ColumnIndex);
                String pis5Verb = cursor.getString(pis5ColumnIndex);
                String futuro1Verb = cursor.getString(futuro1ColumnIndex);
                String futuro2Verb = cursor.getString(futuro2ColumnIndex);
                String futuro3Verb = cursor.getString(futuro3ColumnIndex);
                String futuro4Verb = cursor.getString(futuro4ColumnIndex);
                String futuro5Verb = cursor.getString(futuro5ColumnIndex);
                String pmqpc1Verb = cursor.getString(pmqpc1ColumnIndex);
                String pmqpc2Verb = cursor.getString(pmqpc2ColumnIndex);
                String pmqpc3Verb = cursor.getString(pmqpc3ColumnIndex);
                String pmqpc4Verb = cursor.getString(pmqpc4ColumnIndex);
                String pmqpc5Verb = cursor.getString(pmqpc5ColumnIndex);
                String ppc1Verb = cursor.getString(ppc1ColumnIndex);
                String ppc2Verb = cursor.getString(ppc2ColumnIndex);
                String ppc3Verb = cursor.getString(ppc3ColumnIndex);
                String ppc4Verb = cursor.getString(ppc4ColumnIndex);
                String ppc5Verb = cursor.getString(ppc5ColumnIndex);

                if (TextUtils.isEmpty(engVerb)) {
                    engVerb = getString(R.string.no_meaning);
                }

                TextView mainText = (TextView) findViewById(R.id.details_main_text);
                mainText.setText("\b" + mPorVerb + "\b" + " (" + engVerb + ")");

                ArrayList<DetailsData> data = new ArrayList<>();

                data.add(new DetailsData(getString(R.string.presente_do_indicativo_present_tense),
                        presente1Verb,
                        presente2Verb,
                        presente3Verb,
                        presente4Verb,
                        presente5Verb));
                data.add(new DetailsData(getString(R.string.pret_rito_perfeito_simples_past_tense),
                        pps1Verb,
                        pps2Verb,
                        pps3Verb,
                        pps4Verb,
                        pps5Verb));
                data.add(new DetailsData(getString(R.string.pret_rito_imperfeito_simples_past_tense),
                        pis1Verb,
                        pis2Verb,
                        pis3Verb,
                        pis4Verb,
                        pis5Verb));
                data.add(new DetailsData(getString(R.string.futuro_imperfeito_future_tense),
                        futuro1Verb,
                        futuro2Verb,
                        futuro3Verb,
                        futuro4Verb,
                        futuro5Verb));
                data.add(new DetailsData(getString(R.string.preterito_mais_que_perfeito_composto),
                        pmqpc1Verb,
                        pmqpc2Verb,
                        pmqpc3Verb,
                        pmqpc4Verb,
                        pmqpc5Verb));
                data.add(new DetailsData(getString(R.string.preterito_perfeito_composto),
                        ppc1Verb,
                        ppc2Verb,
                        ppc3Verb,
                        ppc4Verb,
                        ppc5Verb));

                setTitle(mPorVerb);

                DetailsAdapter detailsAdapter = new DetailsAdapter(data, getApplication());

                LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(DetailsActivity.this, LinearLayoutManager.HORIZONTAL, false);
                RecyclerView myRecycleView = (RecyclerView) findViewById(R.id.my_recycler_view);
                myRecycleView.setLayoutManager(horizontalLayoutManager);
                myRecycleView.setAccessibilityDelegateCompat(myRecycleView.getCompatAccessibilityDelegate());
                myRecycleView.setAdapter(detailsAdapter);
                detailsAdapter.setClickListener(this);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu can be updated (some menu items can be hidden or made visible).
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem menuItem = menu.findItem(R.id.action_save);
        menuItem.setVisible(false);
        MenuItem fillUpMenuItem = menu.findItem(R.id.action_fillup);
        fillUpMenuItem.setVisible(false);
        return true;
    }

    private void toastView(String msg) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void ItemClicked(View v, int position) {
        Intent intent = new Intent(DetailsActivity.this, TensesDetailsViewActivity.class);
        Uri currentPetUri = ContentUris.withAppendedId(DataContract.DataEntry.CONTENT_URI, (position + 1));
        intent.setData(currentPetUri);
        intent.putExtra("position", position);
        startActivity(intent);
    }
}