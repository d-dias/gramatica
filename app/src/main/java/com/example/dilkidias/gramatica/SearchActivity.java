package com.example.dilkidias.gramatica;

import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dilkidias.gramatica.Data.DataContract;

/**
 * Created by DILKI DIAS on 26-Jul-17.
 */

public class SearchActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private VerbCursorAdapter searchCursorAdapter;
    private Spinner mSpinner;
    public static String searchWord;
    private static final int VERB_LOADER = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchCursorAdapter = new VerbCursorAdapter(this, null);

        ListView searchList = (ListView) findViewById(R.id.main_search_list);
        searchList.setAdapter(searchCursorAdapter);

        // set up on item click
        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewVerb(id);
            }
        });

        searchList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showPopupMenu(view, id);
                return true;
            }
        });

        mSpinner = (Spinner) findViewById(R.id.search_spinner);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getLoaderManager().destroyLoader(VERB_LOADER);
                getLoaderManager().initLoader(VERB_LOADER, setupSpinner(position),SearchActivity.this);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                getLoaderManager().destroyLoader(VERB_LOADER);
                getLoaderManager().initLoader(VERB_LOADER, setupSpinner(0),SearchActivity.this);
            }
        });

        ImageButton homeButton = (ImageButton) findViewById(R.id.search_home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchActivity.this, MainActivity.class));
            }
        });
    }

    private Boolean showPopupMenu(View view, final Long id){
        PopupMenu popup = new PopupMenu(SearchActivity.this, view);
        popup.getMenuInflater().inflate(R.menu.popup_menu,
                popup.getMenu());
        popup.getMenu().findItem(R.id.deleteVerb).setVisible(false);
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.viewVerb:
                        viewVerb(id);
                        return true;
                    case R.id.editVerb:
                        Intent intent = new Intent(SearchActivity.this, EditorActivity.class);
                        Uri currentPetUri = ContentUris.withAppendedId(DataContract.DataEntry.VERBS_CONTENT_URI, id);
                        intent.setData(currentPetUri);
                        startActivity(intent);
                        return true;
                    default:
                        return false;
                }
            }
        });
        return true;
    }

    private void viewVerb(Long id) {
        int position = mSpinner.getSelectedItemPosition();
        Intent intent;
        Uri currentVerbUri;
        switch (position) {
            case 0:
                intent = new Intent(SearchActivity.this, DetailsActivity.class);
                currentVerbUri = ContentUris.withAppendedId(DataContract.DataEntry.VERBS_CONTENT_URI, id);
                intent.setData(currentVerbUri);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(SearchActivity.this, TensesDetailsViewActivity.class);
                currentVerbUri = ContentUris.withAppendedId(DataContract.DataEntry.CONTENT_URI, id);
                intent.setData(currentVerbUri);
                intent.putExtra("position", id.intValue() - 1);
                startActivity(intent);
                break;
        }
    }

    private Bundle setupSpinner(int position){
        String uri = null;
        String where = null;
        switch (position) {
            case 0:
                uri = DataContract.DataEntry.VERBS_CONTENT_URI.toString();
                where = DataContract.DataEntry.COLUMN_VERB_POR + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_VERB_ENG + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_PRESENTE_1 + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_PRESENTE_2 + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_PRESENTE_3 + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_PRESENTE_4 + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_PRESENTE_5 + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_P_P_S_1 + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_P_P_S_2 + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_P_P_S_3 + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_P_P_S_4 + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_P_P_S_5 + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_P_I_S_1 + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_P_I_S_2 + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_P_I_S_3 + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_P_I_S_4 + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_P_I_S_5 + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_FUTURO_1 + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_FUTURO_2 + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_FUTURO_3 + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_FUTURO_4 + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_FUTURO_5 + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_FUTURO_1 + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_P_M_Q_P_C_1 + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_P_M_Q_P_C_2 + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_P_M_Q_P_C_3 + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_P_M_Q_P_C_4 + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_P_M_Q_P_C_5 + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_P_P_C_1 + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_P_P_C_2 + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_P_P_C_3 + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_P_P_C_4 + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_P_P_C_5 + " LIKE '%" + searchWord + "%'";
                break;
            case 1:
                uri = DataContract.DataEntry.CONTENT_URI.toString();
                where = DataContract.DataEntry.COLUMN_TENSE_NAME + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_TENSE_AR_EU + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_TENSE_AR_TU + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_TENSE_AR_ELE + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_TENSE_AR_NOS + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_TENSE_AR_ELES + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_TENSE_ER_EU + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_TENSE_ER_TU + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_TENSE_ER_ELE + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_TENSE_ER_NOS + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_TENSE_ER_ELES + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_TENSE_IR_EU + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_TENSE_IR_TU + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_TENSE_IR_ELE + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_TENSE_IR_NOS + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_TENSE_IR_ELES + " LIKE '%" + searchWord + "%'" + "OR " +
                        DataContract.DataEntry.COLUMN_IRR + " LIKE '%" + searchWord + "%'";
        }
        Bundle bundle = new Bundle();
        bundle.putString("uri", uri);
        bundle.putString("where", where);
        return bundle;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView search = (SearchView) searchItem.getActionView();

        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        search.onActionViewExpanded();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchWord = query;
                getLoaderManager().destroyLoader(VERB_LOADER);
                getLoaderManager().initLoader(VERB_LOADER, setupSpinner(mSpinner.getSelectedItemPosition()), SearchActivity.this);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchWord = newText;
                getLoaderManager().destroyLoader(VERB_LOADER);
                getLoaderManager().initLoader(VERB_LOADER, setupSpinner(mSpinner.getSelectedItemPosition()), SearchActivity.this);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Respond to a click on the "Up" arrow button in the app bar
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] column = {"*"};

        String where = DataContract.DataEntry.COLUMN_VERB_POR + " LIKE '%" + searchWord + "%'" + "OR " +
                DataContract.DataEntry.COLUMN_VERB_ENG + " LIKE '%" + searchWord + "%'" + "OR " +
                DataContract.DataEntry.COLUMN_PRESENTE_1 + " LIKE '%" + searchWord + "%'" + "OR " +
                DataContract.DataEntry.COLUMN_PRESENTE_2 + " LIKE '%" + searchWord + "%'" + "OR " +
                DataContract.DataEntry.COLUMN_PRESENTE_3 + " LIKE '%" + searchWord + "%'" + "OR " +
                DataContract.DataEntry.COLUMN_PRESENTE_4 + " LIKE '%" + searchWord + "%'" + "OR " +
                DataContract.DataEntry.COLUMN_PRESENTE_5 + " LIKE '%" + searchWord + "%'" + "OR " +
                DataContract.DataEntry.COLUMN_P_P_S_1 + " LIKE '%" + searchWord + "%'" + "OR " +
                DataContract.DataEntry.COLUMN_P_P_S_2 + " LIKE '%" + searchWord + "%'" + "OR " +
                DataContract.DataEntry.COLUMN_P_P_S_3 + " LIKE '%" + searchWord + "%'" + "OR " +
                DataContract.DataEntry.COLUMN_P_P_S_4 + " LIKE '%" + searchWord + "%'" + "OR " +
                DataContract.DataEntry.COLUMN_P_P_S_5 + " LIKE '%" + searchWord + "%'" + "OR " +
                DataContract.DataEntry.COLUMN_P_I_S_1 + " LIKE '%" + searchWord + "%'" + "OR " +
                DataContract.DataEntry.COLUMN_P_I_S_2 + " LIKE '%" + searchWord + "%'" + "OR " +
                DataContract.DataEntry.COLUMN_P_I_S_3 + " LIKE '%" + searchWord + "%'" + "OR " +
                DataContract.DataEntry.COLUMN_P_I_S_4 + " LIKE '%" + searchWord + "%'" + "OR " +
                DataContract.DataEntry.COLUMN_P_I_S_5 + " LIKE '%" + searchWord + "%'" + "OR " +
                DataContract.DataEntry.COLUMN_FUTURO_1 + " LIKE '%" + searchWord + "%'" + "OR " +
                DataContract.DataEntry.COLUMN_FUTURO_2 + " LIKE '%" + searchWord + "%'" + "OR " +
                DataContract.DataEntry.COLUMN_FUTURO_3 + " LIKE '%" + searchWord + "%'" + "OR " +
                DataContract.DataEntry.COLUMN_FUTURO_4 + " LIKE '%" + searchWord + "%'" + "OR " +
                DataContract.DataEntry.COLUMN_FUTURO_5 + " LIKE '%" + searchWord + "%'" + "OR " +
                DataContract.DataEntry.COLUMN_FUTURO_1 + " LIKE '%" + searchWord + "%'" + "OR " +
                DataContract.DataEntry.COLUMN_P_M_Q_P_C_1 + " LIKE '%" + searchWord + "%'" + "OR " +
                DataContract.DataEntry.COLUMN_P_M_Q_P_C_2 + " LIKE '%" + searchWord + "%'" + "OR " +
                DataContract.DataEntry.COLUMN_P_M_Q_P_C_3 + " LIKE '%" + searchWord + "%'" + "OR " +
                DataContract.DataEntry.COLUMN_P_M_Q_P_C_4 + " LIKE '%" + searchWord + "%'" + "OR " +
                DataContract.DataEntry.COLUMN_P_M_Q_P_C_5 + " LIKE '%" + searchWord + "%'" + "OR " +
                DataContract.DataEntry.COLUMN_P_P_C_1 + " LIKE '%" + searchWord + "%'" + "OR " +
                DataContract.DataEntry.COLUMN_P_P_C_2 + " LIKE '%" + searchWord + "%'" + "OR " +
                DataContract.DataEntry.COLUMN_P_P_C_3 + " LIKE '%" + searchWord + "%'" + "OR " +
                DataContract.DataEntry.COLUMN_P_P_C_4 + " LIKE '%" + searchWord + "%'" + "OR " +
                DataContract.DataEntry.COLUMN_P_P_C_5 + " LIKE '%" + searchWord + "%'";
        if (args.getString("where") != null) {
            where = args.getString("where");
        }

        Uri uri = DataContract.DataEntry.VERBS_CONTENT_URI;
        if (args.getString("uri") != null) {
            uri = Uri.parse(args.getString("uri"));
        }

        return new CursorLoader(this, // Parent activity context
                uri, // Provider content URI to query
                column, // Columns to include in the resulting Cursor
                where, null, null);// No selection clause
        // No selection arguments
        // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null) {
            Toast.makeText(SearchActivity.this, "No records found", Toast.LENGTH_LONG).show();
        }

        searchCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        searchCursorAdapter.swapCursor(null);
    }
}
