package com.example.dilkidias.gramatica;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.dilkidias.gramatica.Data.DataContract;
import com.example.dilkidias.gramatica.Data.DataContract.DataEntry;

import java.util.ArrayList;

import static com.example.dilkidias.gramatica.Data.DataContract.DataEntry.*;

/**
 * Created by DILKI DIAS on 29-Jul-17.
 */

public class TensesDetailsViewActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, DetailsAdapter.ClickListener {
    private int position;
    private LinearLayoutManager horizontalLayoutManager;
    private LinearLayoutManager horizontalLayoutManager_irr;
    private ArrayList<Integer> id;
    private TextView tenseText;
    private RecyclerView mainTable;
    private RecyclerView irrTable;

    private static final int IRR_LOADER = 1;

    /**
     * Identifier for the pet data loader
     */
    private static final int EXISTING_PET_LOADER = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tenses_details);

        id = new ArrayList();
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        Uri mCurrentUri = intent.getData();

        tenseText = (TextView) findViewById(R.id.tense_name);

        DetailsAdapter detailsAdapter = new DetailsAdapter(null, getApplication());
        horizontalLayoutManager = new LinearLayoutManager(TensesDetailsViewActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mainTable = (RecyclerView) findViewById(R.id.tense_main_table);
        mainTable.setLayoutManager(horizontalLayoutManager);
        mainTable.setAdapter(detailsAdapter);

        Bundle mainB = new Bundle();
        mainB.putString("uri", mCurrentUri.toString());
        mainB.putString("where", null);
        mainB.putStringArray("projection", null);
        getLoaderManager().initLoader(EXISTING_PET_LOADER, mainB, this);

        horizontalLayoutManager_irr = new LinearLayoutManager(TensesDetailsViewActivity.this, LinearLayoutManager.HORIZONTAL, false);
        DetailsAdapter adapter = new DetailsAdapter(null, getApplication());
        irrTable = (RecyclerView) findViewById(R.id.tense_irr_table);
        irrTable.setLayoutManager(horizontalLayoutManager_irr);
        irrTable.setAdapter(adapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Define a projection that specifies the columns from the table we care about.
        String[] projection = args.getStringArray("projection");
        String where = args.getString("where");
        Uri uri = Uri.parse(args.getString("uri"));

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this, // Parent activity context
                uri, // Provider content URI to query
                projection, // Columns to include in the resulting Cursor
                where, null, null);// No selection clause
        // No selection arguments
        // Default sort order
    }

//    private lateinit var projection: Array<String>

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        int loaderId = loader.getId();
        switch (loaderId) {
            case 0:
                if (cursor.moveToFirst()) {
                    // Find the columns of pet attributes that we're interested in
                    int idColumnIndex = cursor.getColumnIndex(TENSES_ID);
                    int nameColumnIndex = cursor.getColumnIndex(COLUMN_TENSE_NAME);
                    int detailsColumnIndex = cursor.getColumnIndex(COLUMN_TENSE_DETAILS);
                    int tableColumnIndex = cursor.getColumnIndex(COLUMN_TENSE_TABLE);

                    int num_irrColumnIndex = cursor.getColumnIndex(COLUMN_NUM_IRR_VERBS);
                    int irr_verbsColumnIndex = cursor.getColumnIndex(COLUMN_IRR);

                    int id = cursor.getInt(idColumnIndex);
                    String name = cursor.getString(nameColumnIndex);
                    String details = cursor.getString(detailsColumnIndex);
                    int table = cursor.getInt(tableColumnIndex);

                    int num_irr_verbs = cursor.getInt(num_irrColumnIndex);
                    String irr_verbs_string = cursor.getString(irr_verbsColumnIndex);

                    TextView exText = (TextView) findViewById(R.id.text_reg_ex);
                    FrameLayout mainFrame = (FrameLayout) findViewById(R.id.main_frame_layout);
                    TextView textReg = (TextView) findViewById(R.id.text_reg);
                    ViewStub frameEx = (ViewStub) findViewById(R.id.frame_layout_ex);

                    ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.main_cons);
                    ConstraintSet set = new ConstraintSet();

                    /* There is a table column in db that define witch type of table it should have.
                     0--> full table with ar,er and ir
                      1--> half table with one column at start
                      2--> half table with one column at end
                      3--> without table
                      4-->
                      5--> gerúmdio table
                     */
                    switch (table){
                        case 0:
                            int ar_eu1ColumnIndex = cursor.getColumnIndex(COLUMN_TENSE_AR_EU);
                            int ar_tuColumnIndex = cursor.getColumnIndex(COLUMN_TENSE_AR_TU);
                            int ar_eleColumnIndex = cursor.getColumnIndex(COLUMN_TENSE_AR_ELE);
                            int ar_nosColumnIndex = cursor.getColumnIndex(COLUMN_TENSE_AR_NOS);
                            int ar_elesColumnIndex = cursor.getColumnIndex(COLUMN_TENSE_AR_ELES);
                            int er_euColumnIndex = cursor.getColumnIndex(COLUMN_TENSE_ER_EU);
                            int er_tuColumnIndex = cursor.getColumnIndex(COLUMN_TENSE_ER_TU);
                            int er_eleColumnIndex = cursor.getColumnIndex(COLUMN_TENSE_ER_ELE);
                            int er_nosColumnIndex = cursor.getColumnIndex(COLUMN_TENSE_ER_NOS);
                            int er_elesColumnIndex = cursor.getColumnIndex(COLUMN_TENSE_ER_ELES);
                            int ir_euColumnIndex = cursor.getColumnIndex(COLUMN_TENSE_IR_EU);
                            int ir_tuColumnIndex = cursor.getColumnIndex(COLUMN_TENSE_IR_TU);
                            int ir_eleColumnIndex = cursor.getColumnIndex(COLUMN_TENSE_IR_ELE);
                            int ir_nosColumnIndex = cursor.getColumnIndex(COLUMN_TENSE_IR_NOS);
                            int ir_elesColumnIndex = cursor.getColumnIndex(COLUMN_TENSE_IR_ELES);

                            // Extract out the value from the Cursor for the given column index

                            String ar_eu = cursor.getString(ar_eu1ColumnIndex);
                            String ar_tu = cursor.getString(ar_tuColumnIndex);
                            String ar_ele = cursor.getString(ar_eleColumnIndex);
                            String ar_nos = cursor.getString(ar_nosColumnIndex);
                            String ar_eles = cursor.getString(ar_elesColumnIndex);
                            String er_eu = cursor.getString(er_euColumnIndex);
                            String er_tu = cursor.getString(er_tuColumnIndex);
                            String er_ele = cursor.getString(er_eleColumnIndex);
                            String er_nos = cursor.getString(er_nosColumnIndex);
                            String er_eles = cursor.getString(er_elesColumnIndex);
                            String ir_eu = cursor.getString(ir_euColumnIndex);
                            String ir_tu = cursor.getString(ir_tuColumnIndex);
                            String ir_ele = cursor.getString(ir_eleColumnIndex);
                            String ir_nos = cursor.getString(ir_nosColumnIndex);
                            String ir_eles = cursor.getString(ir_elesColumnIndex);

                            ArrayList<DetailsData> data_reg = new ArrayList<DetailsData>();

                            data_reg.add(new DetailsData("-ar verbs",
                                    ar_eu,
                                    ar_tu,
                                    ar_ele,
                                    ar_nos,
                                    ar_eles));
                            data_reg.add(new DetailsData("-er verbs",
                                    er_eu,
                                    er_tu,
                                    er_ele,
                                    er_nos,
                                    er_eles));
                            data_reg.add(new DetailsData("-ir verbs",
                                    ir_eu,
                                    ir_tu,
                                    ir_ele,
                                    ir_nos,
                                    ir_eles));

                            DetailsAdapter detailsAdapter = new DetailsAdapter(data_reg, getApplication());

                            mainTable.setLayoutManager(horizontalLayoutManager);
                            mainTable.setAdapter(detailsAdapter);

                            exText.setVisibility(View.GONE);
                            break;
                        case 1:
                            int euColumnIndex = cursor.getColumnIndex(COLUMN_TENSE_AR_EU);
                            int tuColumnIndex = cursor.getColumnIndex(COLUMN_TENSE_AR_TU);
                            int eleColumnIndex = cursor.getColumnIndex(COLUMN_TENSE_AR_ELE);
                            int nosColumnIndex = cursor.getColumnIndex(COLUMN_TENSE_AR_NOS);
                            int elesColumnIndex = cursor.getColumnIndex(COLUMN_TENSE_AR_ELES);
                            int ColumnIndex = cursor.getColumnIndex(COLUMN_TENSE_ER_EU);

                            String eu = cursor.getString(euColumnIndex);
                            String tu = cursor.getString(tuColumnIndex);
                            String ele = cursor.getString(eleColumnIndex);
                            String nos = cursor.getString(nosColumnIndex);
                            String eles = cursor.getString(elesColumnIndex);
                            String column = cursor.getString(ColumnIndex);

                            ArrayList<DetailsData> data_reg_1 = new ArrayList<DetailsData>();

                            data_reg_1.add(new DetailsData("Ter presente",
                                    eu,
                                    tu,
                                    ele,
                                    nos,
                                    eles));

                            DetailsAdapter detailsAdapter_1 = new DetailsAdapter(data_reg_1, getApplication());

                            mainTable.setLayoutManager(horizontalLayoutManager);
                            mainTable.setAdapter(detailsAdapter_1);

                            exText.setText("+ " + column);
                            mainTable.setPadding(0, 0, 0, 0);
                            break;
                        case 2:
                            set.clone(layout);
                            set.connect(exText.getId(), ConstraintSet.LEFT, layout.getId(), ConstraintSet.LEFT, 0);
                            set.connect(exText.getId(), ConstraintSet.RIGHT, mainFrame.getId() , ConstraintSet.LEFT, 0);
                            set.connect(mainFrame.getId(), ConstraintSet.LEFT, exText.getId() , ConstraintSet.RIGHT, 0);
                            set.connect(mainTable.getId(), ConstraintSet.RIGHT, layout.getId() , ConstraintSet.RIGHT, 0);
                            set.applyTo(layout);

                            int euColumn = cursor.getColumnIndex(COLUMN_TENSE_IR_EU);
                            int tuColumn = cursor.getColumnIndex(COLUMN_TENSE_IR_TU);
                            int eleColumn = cursor.getColumnIndex(COLUMN_TENSE_IR_ELE);
                            int nosColumn = cursor.getColumnIndex(COLUMN_TENSE_IR_NOS);
                            int elesColumn = cursor.getColumnIndex(COLUMN_TENSE_IR_ELES);
                            int Column = cursor.getColumnIndex(COLUMN_TENSE_AR_EU);

                            String eu1 = cursor.getString(euColumn);
                            String tu1 = cursor.getString(tuColumn);
                            String ele1 = cursor.getString(eleColumn);
                            String nos1 = cursor.getString(nosColumn);
                            String eles1 = cursor.getString(elesColumn);
                            String column1 = cursor.getString(Column);

                            ArrayList<DetailsData> data_reg_2 = new ArrayList<DetailsData>();

                            data_reg_2.add(new DetailsData("-ar/-er/-ir",
                                    eu1,
                                    tu1,
                                    ele1,
                                    nos1,
                                    eles1));

                            DetailsAdapter detailsAdapter_2 = new DetailsAdapter(data_reg_2, getApplication());

                            mainTable.setLayoutManager(horizontalLayoutManager);
                            mainTable.setAdapter(detailsAdapter_2);

                            exText.setText(column1 + " +");
                            break;
                        case 3:
                            mainFrame.setVisibility(View.GONE);
                            mainTable.setVisibility(View.GONE);

                            set.clone(layout);
                            set.connect(exText.getId(), ConstraintSet.LEFT, layout.getId(), ConstraintSet.LEFT, 0);
                            set.connect(exText.getId(), ConstraintSet.RIGHT, layout.getId() , ConstraintSet.RIGHT, 0);
                            set.connect(exText.getId(), ConstraintSet.TOP, textReg.getId() , ConstraintSet.BOTTOM, 8);
                            set.clear(exText.getId(), ConstraintSet.BOTTOM);
                            set.applyTo(layout);

                            int columnIndex = cursor.getColumnIndex(COLUMN_TENSE_AR_EU);
                            String column2 = cursor.getString(columnIndex);

                            exText.setText(column2);
                            break;
                        case 4:
                            mainFrame.setVisibility(View.GONE);
                            mainTable.setVisibility(View.GONE);
                            exText.setVisibility(View.GONE);

                            frameEx.setLayoutResource(R.layout.imperativo_regular);
                            frameEx.inflate();
                            frameEx.setVisibility(View.VISIBLE);
                            break;
                        case 5:
                            mainFrame.setVisibility(View.GONE);
                            mainTable.setVisibility(View.GONE);
                            exText.setVisibility(View.GONE);

                            frameEx.setLayoutResource(R.layout.gerundio_table);
                            frameEx.inflate();
                            frameEx.setVisibility(View.VISIBLE);
                            break;
                    }

                    setTitle(name);

                    tenseText.setText(name);
                    TextView textDetails = (TextView) findViewById(R.id.tense_details);
                    textDetails.setText(details);

                    if (!TextUtils.isEmpty(irr_verbs_string) && num_irr_verbs > 0) {
                        String[] irrArray = irr_verbs_string.split(",");
                        Bundle bundle = new Bundle();
                        bundle.putString("uri", VERBS_CONTENT_URI.toString());
                        String where = COLUMN_VERB_POR + " LIKE '%" + irrArray[0] + "%'";
                        for (int i = 0; i < irrArray.length - 1; i++) {
                            where += "OR " + COLUMN_VERB_POR + " LIKE '%" + irrArray[i] + "%'";
                        }
                        bundle.putString("where", where);

                        String[] projection = null;
                        switch (id) {
                            case 1:
                                projection = new String[]{_ID,
                                        COLUMN_VERB_POR,
                                        COLUMN_VERB_ENG,
                                        COLUMN_PRESENTE_1,
                                        COLUMN_PRESENTE_2,
                                        COLUMN_PRESENTE_3,
                                        COLUMN_PRESENTE_4,
                                        COLUMN_PRESENTE_5};
                                break;
                            case 2:
                                projection = new String[]{_ID,
                                        COLUMN_VERB_POR,
                                        COLUMN_VERB_ENG,
                                        COLUMN_P_P_S_1,
                                        COLUMN_P_P_S_2,
                                        COLUMN_P_P_S_3,
                                        COLUMN_P_P_S_4,
                                        COLUMN_P_P_S_5};
                                break;
                            case 3:
                                projection = new String[]{_ID,
                                        COLUMN_VERB_POR,
                                        COLUMN_VERB_ENG,
                                        COLUMN_P_I_S_1,
                                        COLUMN_P_I_S_2,
                                        COLUMN_P_I_S_3,
                                        COLUMN_P_I_S_4,
                                        COLUMN_P_I_S_5};
                                break;
                            case 4:
                                projection = new String[]{_ID,
                                        COLUMN_VERB_POR,
                                        COLUMN_VERB_ENG,
                                        COLUMN_FUTURO_1,
                                        COLUMN_FUTURO_2,
                                        COLUMN_FUTURO_3,
                                        COLUMN_FUTURO_4,
                                        COLUMN_FUTURO_5};
                                break;
                            case 5:
                                projection = new String[]{_ID,
                                        COLUMN_VERB_POR,
                                        COLUMN_VERB_ENG,
                                        COLUMN_P_M_Q_P_C_1,
                                        COLUMN_P_M_Q_P_C_2,
                                        COLUMN_P_M_Q_P_C_3,
                                        COLUMN_P_M_Q_P_C_4,
                                        COLUMN_P_M_Q_P_C_5};
                                break;
                            case 6:
                                projection = new String[]{_ID,
                                        COLUMN_VERB_POR,
                                        COLUMN_VERB_ENG,
                                        COLUMN_P_P_C_1,
                                        COLUMN_P_P_C_2,
                                        COLUMN_P_P_C_3,
                                        COLUMN_P_P_C_4,
                                        COLUMN_P_P_C_5};
                                break;
                        }// TODO: 08/09/2017 new table for imparetivo--@imperative_irregular
                        bundle.putStringArray("projection", projection);
                        cursor.close();
                        getLoaderManager().initLoader(IRR_LOADER, bundle, this);
                    } else {
                        TextView textIrr = (TextView) findViewById(R.id.text_irr);
                        textIrr.setVisibility(View.GONE);
                        FrameLayout irrFrame = (FrameLayout) findViewById(R.id.irr_frame_layout);
                        irrFrame.setVisibility(View.GONE);
                        irrTable.setVisibility(View.GONE);
                        mainTable.setPadding(0,0,15,0);
                    }
                }
                break;
            case 1:
                if (cursor.moveToFirst()) {
                    ArrayList<DetailsData> data = new ArrayList<DetailsData>();
                    String verb1 = null;
                    String verb2 = null;
                    String verb3 = null;
                    String verb4 = null;
                    String verb5 = null;
                    for (int i = 0; i < cursor.getCount(); i++) {
                        if (i != 0) {
                            cursor.moveToNext();
                        }
                        int idColumnIndex = cursor.getColumnIndex(_ID);
                        int porColumnIndex = cursor.getColumnIndex(COLUMN_VERB_POR);
                        int engColumnIndex = cursor.getColumnIndex(COLUMN_VERB_ENG);
                        String mPorVerb = cursor.getString(porColumnIndex);
                        String engVerb = cursor.getString(engColumnIndex);

                        id.add(cursor.getInt(idColumnIndex));
                        switch (position) {
                            case 0:
                                int presente1ColumnIndex = cursor.getColumnIndex(COLUMN_PRESENTE_1);
                                int presente2ColumnIndex = cursor.getColumnIndex(COLUMN_PRESENTE_2);
                                int presente3ColumnIndex = cursor.getColumnIndex(COLUMN_PRESENTE_3);
                                int presente4ColumnIndex = cursor.getColumnIndex(COLUMN_PRESENTE_4);
                                int presente5ColumnIndex = cursor.getColumnIndex(COLUMN_PRESENTE_5);

                                verb1 = cursor.getString(presente1ColumnIndex);
                                verb2 = cursor.getString(presente2ColumnIndex);
                                verb3 = cursor.getString(presente3ColumnIndex);
                                verb4 = cursor.getString(presente4ColumnIndex);
                                verb5 = cursor.getString(presente5ColumnIndex);
                                break;
                            case 1:
                                int pps1ColumnIndex = cursor.getColumnIndex(COLUMN_P_P_S_1);
                                int pps2ColumnIndex = cursor.getColumnIndex(COLUMN_P_P_S_2);
                                int pps3ColumnIndex = cursor.getColumnIndex(COLUMN_P_P_S_3);
                                int pps4ColumnIndex = cursor.getColumnIndex(COLUMN_P_P_S_4);
                                int pps5ColumnIndex = cursor.getColumnIndex(COLUMN_P_P_S_5);

                                verb1 = cursor.getString(pps1ColumnIndex);
                                verb2 = cursor.getString(pps2ColumnIndex);
                                verb3 = cursor.getString(pps3ColumnIndex);
                                verb4 = cursor.getString(pps4ColumnIndex);
                                verb5 = cursor.getString(pps5ColumnIndex);
                                break;
                            case 2:
                                int pis1ColumnIndex = cursor.getColumnIndex(COLUMN_P_I_S_1);
                                int pis2ColumnIndex = cursor.getColumnIndex(COLUMN_P_I_S_2);
                                int pis3ColumnIndex = cursor.getColumnIndex(COLUMN_P_I_S_3);
                                int pis4ColumnIndex = cursor.getColumnIndex(COLUMN_P_I_S_4);
                                int pis5ColumnIndex = cursor.getColumnIndex(COLUMN_P_I_S_5);

                                verb1 = cursor.getString(pis1ColumnIndex);
                                verb2 = cursor.getString(pis2ColumnIndex);
                                verb3 = cursor.getString(pis3ColumnIndex);
                                verb4 = cursor.getString(pis4ColumnIndex);
                                verb5 = cursor.getString(pis5ColumnIndex);
                                break;
                            case 3:
                                int futuro1ColumnIndex = cursor.getColumnIndex(COLUMN_FUTURO_1);
                                int futuro2ColumnIndex = cursor.getColumnIndex(COLUMN_FUTURO_2);
                                int futuro3ColumnIndex = cursor.getColumnIndex(COLUMN_FUTURO_3);
                                int futuro4ColumnIndex = cursor.getColumnIndex(COLUMN_FUTURO_4);
                                int futuro5ColumnIndex = cursor.getColumnIndex(COLUMN_FUTURO_5);

                                verb1 = cursor.getString(futuro1ColumnIndex);
                                verb2 = cursor.getString(futuro2ColumnIndex);
                                verb3 = cursor.getString(futuro3ColumnIndex);
                                verb4 = cursor.getString(futuro4ColumnIndex);
                                verb5 = cursor.getString(futuro5ColumnIndex);
                                break;
                            case 4:
                                int pmqpc1ColumnIndex = cursor.getColumnIndex(COLUMN_P_M_Q_P_C_1);
                                int pmqpc2ColumnIndex = cursor.getColumnIndex(COLUMN_P_M_Q_P_C_2);
                                int pmqpc3ColumnIndex = cursor.getColumnIndex(COLUMN_P_M_Q_P_C_3);
                                int pmqpc4ColumnIndex = cursor.getColumnIndex(COLUMN_P_M_Q_P_C_4);
                                int pmqpc5ColumnIndex = cursor.getColumnIndex(COLUMN_P_M_Q_P_C_5);

                                verb1 = cursor.getString(pmqpc1ColumnIndex);
                                verb2 = cursor.getString(pmqpc2ColumnIndex);
                                verb3 = cursor.getString(pmqpc3ColumnIndex);
                                verb4 = cursor.getString(pmqpc4ColumnIndex);
                                verb5 = cursor.getString(pmqpc5ColumnIndex);
                                break;
                            case 5:
                                int ppc1ColumnIndex = cursor.getColumnIndex(COLUMN_P_P_C_1);
                                int ppc2ColumnIndex = cursor.getColumnIndex(COLUMN_P_P_C_2);
                                int ppc3ColumnIndex = cursor.getColumnIndex(COLUMN_P_P_C_3);
                                int ppc4ColumnIndex = cursor.getColumnIndex(COLUMN_P_P_C_4);
                                int ppc5ColumnIndex = cursor.getColumnIndex(COLUMN_P_P_C_5);

                                verb1 = cursor.getString(ppc1ColumnIndex);
                                verb2 = cursor.getString(ppc2ColumnIndex);
                                verb3 = cursor.getString(ppc3ColumnIndex);
                                verb4 = cursor.getString(ppc4ColumnIndex);
                                verb5 = cursor.getString(ppc5ColumnIndex);
                                break;
                            // TODO: 02-Sep-17 add other verbs
                            // TODO: 08/09/2017 change the db for imperative irr
                        }
                        data.add(new DetailsData(
                                mPorVerb + "(" + engVerb + ")",
                                verb1,
                                verb2,
                                verb3,
                                verb4,
                                verb5));
                    }

                    DetailsAdapter adapter = new DetailsAdapter(data, getApplication());
                    irrTable.setLayoutManager(horizontalLayoutManager_irr);
                    irrTable.setAdapter(adapter);
                    adapter.setClickListener(this);
                }
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        id = new ArrayList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tense_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.action_home:
                startActivity(new Intent(TensesDetailsViewActivity.this, MainActivity.class));
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_search:
                startActivity(new Intent(TensesDetailsViewActivity.this, SearchActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void ItemClicked(View v, int position) {
        Intent intent = new Intent(TensesDetailsViewActivity.this, DetailsActivity.class);
        int item_id = id.get(position);
        Uri currentPetUri = ContentUris.withAppendedId(VERBS_CONTENT_URI, item_id);
        intent.setData(currentPetUri);
        startActivity(intent);
    }
}
