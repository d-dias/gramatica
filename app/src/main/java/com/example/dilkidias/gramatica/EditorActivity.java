package com.example.dilkidias.gramatica;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

import com.example.dilkidias.gramatica.Data.DataContract;
import com.example.dilkidias.gramatica.Data.DbOpenHelper;

import static com.example.dilkidias.gramatica.Data.DataContract.DataEntry.*;
import static com.example.dilkidias.gramatica.Data.DataContract.DataEntry.COLUMN_PRESENTE_1;
import static com.example.dilkidias.gramatica.Data.DataContract.DataEntry.COLUMN_PRESENTE_2;
import static com.example.dilkidias.gramatica.Data.DataContract.DataEntry.COLUMN_VERB_ENG;

public class EditorActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private String porVerb;

    private static final String LOG_TAG = null;
    /**
     * EditText field to enter the pet's name
     */
    private EditText mPorVerbEditText;

    /**
     * EditText field to enter the pet's breed
     */
    private EditText mEngVerbEditText;

    private EditText mPresente1EditText;
    private EditText mPresente2EditText;
    private EditText mPresente3EditText;
    private EditText mPresente4EditText;
    private EditText mPresente5EditText;
    private EditText mPps1EditText;
    private EditText mPps2EditText;
    private EditText mPps3EditText;
    private EditText mPps4EditText;
    private EditText mPps5EditText;
    private EditText mPis1EditText;
    private EditText mPis2EditText;
    private EditText mPis3EditText;
    private EditText mPis4EditText;
    private EditText mPis5EditText;
    private EditText mFuturo1EditText;
    private EditText mFuturo2EditText;
    private EditText mFuturo3EditText;
    private EditText mFuturo4EditText;
    private EditText mFuturo5EditText;
    private EditText mPmqpc1EditText;
    private EditText mPmqpc2EditText;
    private EditText mPmqpc3EditText;
    private EditText mPmqpc4EditText;
    private EditText mPmqpc5EditText;
    private EditText mPpc1EditText;
    private EditText mPpc2EditText;
    private EditText mPpc3EditText;
    private EditText mPpc4EditText;
    private EditText mPpc5EditText;

    /**
     * EditText field to enter the pet's gender
     */
    private Spinner mSpinner;

    /**
     * Content URI for the existing pet (null if it's a new pet)
     */
    private Uri mCurrentVerbUri;

    /**
     * Identifier for the pet data loader
     */
    private static final int EXISTING_VERB_LOADER = 1;

    private boolean mVerbHasChanged = false;
    private Toast toast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_new);

        Intent intent = getIntent();
        mCurrentVerbUri = intent.getData();

        if (mCurrentVerbUri == null) {
            setTitle("Add new verb");

            // Invalidate the options menu, so the "Delete" menu option can be hidden.
            // (It doesn't make sense to delete a pet that hasn't been created yet.)
            invalidateOptionsMenu();
        } else {
            setTitle("Edit verb");

            // Initialize a loader to read the pet data from the database
            // and display the current values in the editor
            getLoaderManager().initLoader(EXISTING_VERB_LOADER, null, this);
        }

        // Find all relevant views that we will need to read user input from
        mPorVerbEditText = (EditText) findViewById(R.id.edit_verb_por);
        mEngVerbEditText = (EditText) findViewById(R.id.edit_verb_eng);

        mPresente1EditText = (EditText) findViewById(R.id.edit_presente_1);
        mPresente2EditText = (EditText) findViewById(R.id.edit_presente_2);
        mPresente3EditText = (EditText) findViewById(R.id.edit_presente_3);
        mPresente4EditText = (EditText) findViewById(R.id.edit_presente_4);
        mPresente5EditText = (EditText) findViewById(R.id.edit_presente_5);
        mPps1EditText = (EditText) findViewById(R.id.edit_p_p_s_1);
        mPps2EditText = (EditText) findViewById(R.id.edit_p_p_s_2);
        mPps3EditText = (EditText) findViewById(R.id.edit_p_p_s_3);
        mPps4EditText = (EditText) findViewById(R.id.edit_p_p_s_4);
        mPps5EditText = (EditText) findViewById(R.id.edit_p_p_s_5);
        mPis1EditText = (EditText) findViewById(R.id.edit_p_i_s_1);
        mPis2EditText = (EditText) findViewById(R.id.edit_p_i_s_2);
        mPis3EditText = (EditText) findViewById(R.id.edit_p_i_s_3);
        mPis4EditText = (EditText) findViewById(R.id.edit_p_i_s_4);
        mPis5EditText = (EditText) findViewById(R.id.edit_p_i_s_5);
        mFuturo1EditText = (EditText) findViewById(R.id.edit_futuro_1);
        mFuturo2EditText = (EditText) findViewById(R.id.edit_futuro_2);
        mFuturo3EditText = (EditText) findViewById(R.id.edit_futuro_3);
        mFuturo4EditText = (EditText) findViewById(R.id.edit_futuro_4);
        mFuturo5EditText = (EditText) findViewById(R.id.edit_futuro_5);
        mPmqpc1EditText = (EditText) findViewById(R.id.edit_p_m_q_p_c_1);
        mPmqpc2EditText = (EditText) findViewById(R.id.edit_p_m_q_p_c_2);
        mPmqpc3EditText = (EditText) findViewById(R.id.edit_p_m_q_p_c_3);
        mPmqpc4EditText = (EditText) findViewById(R.id.edit_p_m_q_p_c_4);
        mPmqpc5EditText = (EditText) findViewById(R.id.edit_p_m_q_p_c_5);
        mPpc1EditText = (EditText) findViewById(R.id.edit_p_p_c_1);
        mPpc2EditText = (EditText) findViewById(R.id.edit_p_p_c_2);
        mPpc3EditText = (EditText) findViewById(R.id.edit_p_p_c_3);
        mPpc4EditText = (EditText) findViewById(R.id.edit_p_p_c_4);
        mPpc5EditText = (EditText) findViewById(R.id.edit_p_p_c_5);

        mSpinner = (Spinner) findViewById(R.id.edit_spinner);

        setupSpinner();

        mPorVerbEditText.setOnTouchListener(mTouchListener);
        mEngVerbEditText.setOnTouchListener(mTouchListener);

        mPresente1EditText.setOnTouchListener(mTouchListener);
        mPresente2EditText.setOnTouchListener(mTouchListener);
        mPresente3EditText.setOnTouchListener(mTouchListener);
        mPresente4EditText.setOnTouchListener(mTouchListener);
        mPresente5EditText.setOnTouchListener(mTouchListener);
        mPps1EditText.setOnTouchListener(mTouchListener);
        mPps2EditText.setOnTouchListener(mTouchListener);
        mPps3EditText.setOnTouchListener(mTouchListener);
        mPps4EditText.setOnTouchListener(mTouchListener);
        mPps5EditText.setOnTouchListener(mTouchListener);
        mPis1EditText.setOnTouchListener(mTouchListener);
        mPis2EditText.setOnTouchListener(mTouchListener);
        mPis3EditText.setOnTouchListener(mTouchListener);
        mPis4EditText.setOnTouchListener(mTouchListener);
        mPis5EditText.setOnTouchListener(mTouchListener);
        mFuturo1EditText.setOnTouchListener(mTouchListener);
        mFuturo2EditText.setOnTouchListener(mTouchListener);
        mFuturo3EditText.setOnTouchListener(mTouchListener);
        mFuturo4EditText.setOnTouchListener(mTouchListener);
        mFuturo5EditText.setOnTouchListener(mTouchListener);
        mPmqpc1EditText.setOnTouchListener(mTouchListener);
        mPmqpc2EditText.setOnTouchListener(mTouchListener);
        mPmqpc3EditText.setOnTouchListener(mTouchListener);
        mPmqpc4EditText.setOnTouchListener(mTouchListener);
        mPmqpc5EditText.setOnTouchListener(mTouchListener);
        mPpc1EditText.setOnTouchListener(mTouchListener);
        mPpc2EditText.setOnTouchListener(mTouchListener);
        mPpc3EditText.setOnTouchListener(mTouchListener);
        mPpc4EditText.setOnTouchListener(mTouchListener);
        mPpc5EditText.setOnTouchListener(mTouchListener);

        TextView presenteText = (TextView) findViewById(R.id.edit_text_presente);
        TextView ppsText = (TextView) findViewById(R.id.edit_text_p_p_s);
        TextView pisText = (TextView) findViewById(R.id.edit_text_p_i_s);
        TextView futuroText = (TextView) findViewById(R.id.edit_text_futuro);
        TextView pmqpcText = (TextView) findViewById(R.id.edit_text_p_m_q_p_c);
        TextView ppcText = (TextView) findViewById(R.id.edit_text_p_p_c);

        presenteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textPopUp(v);
            }
        });
        ppsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textPopUp(v);
            }
        });
        pisText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textPopUp(v);
            }
        });
        futuroText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textPopUp(v);
            }
        });
        pmqpcText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textPopUp(v);
            }
        });
        ppcText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textPopUp(v);
            }
        });
    }

    private void textPopUp(final View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenuInflater().inflate(R.menu.popup_menu_editor,
                popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.editView:
                        int position = 0;
                        switch (v.getId()){
                            case R.id.edit_text_presente:
                                position = 0;
                                break;
                            case R.id.edit_text_p_p_s:
                                position = 1;
                                break;
                            case R.id.edit_text_p_i_s:
                                position = 2;
                                break;
                            case R.id.edit_text_futuro:
                                position = 3;
                                break;
                            case R.id.edit_text_p_m_q_p_c:
                                position = 4;
                                break;
                            case R.id.edit_text_p_p_c:
                                position = 5;
                                break;
                        }
                        Intent i = new Intent(EditorActivity.this, TensesDetailsViewActivity.class);
                        i.putExtra("position", position);
                        Uri currentUri = ContentUris.withAppendedId(DataContract.DataEntry.CONTENT_URI, (position + 1));
                        i.setData(currentUri);
                        startActivity(i);
                        break;
                    case R.id.editFillup:
                        if (TextUtils.isEmpty(mPorVerbEditText.getText().toString().trim())) {
                            toastView("Add verb to fill up.");
                        } else {
                            String porText = mPorVerbEditText.getText().toString().trim();
                            switch (v.getId()) {
                                case R.id.edit_text_presente:
                                    fillUp(mPresente1EditText, porText);
                                    fillUp(mPresente2EditText, porText);
                                    fillUp(mPresente3EditText, porText);
                                    fillUp(mPresente4EditText, porText);
                                    fillUp(mPresente5EditText, porText);
                                    break;
                                case R.id.edit_text_p_p_s:
                                    fillUp(mPps1EditText, porText);
                                    fillUp(mPps2EditText, porText);
                                    fillUp(mPps3EditText, porText);
                                    fillUp(mPps4EditText, porText);
                                    fillUp(mPps5EditText, porText);
                                    break;
                                case R.id.edit_text_p_i_s:
                                    fillUp(mPis1EditText, porText);
                                    fillUp(mPis2EditText, porText);
                                    fillUp(mPis3EditText, porText);
                                    fillUp(mPis4EditText, porText);
                                    fillUp(mPis5EditText, porText);
                                    break;
                                case R.id.edit_text_futuro:
                                    fillUp(mFuturo1EditText, porText);
                                    fillUp(mFuturo2EditText, porText);
                                    fillUp(mFuturo3EditText, porText);
                                    fillUp(mFuturo4EditText, porText);
                                    fillUp(mFuturo5EditText, porText);
                                    break;
                                case R.id.edit_text_p_m_q_p_c:
                                    fillUp(mPmqpc1EditText, porText);
                                    fillUp(mPmqpc2EditText, porText);
                                    fillUp(mPmqpc3EditText, porText);
                                    fillUp(mPmqpc4EditText, porText);
                                    fillUp(mPmqpc5EditText, porText);
                                    break;
                                case R.id.edit_text_p_p_c:
                                    fillUp(mPpc1EditText, porText);
                                    fillUp(mPpc2EditText, porText);
                                    fillUp(mPpc3EditText, porText);
                                    fillUp(mPpc4EditText, porText);
                                    fillUp(mPpc5EditText, porText);
                                    break;
                            }
                        }
                        break;
                    case R.id.editClear:
                        switch (v.getId()){
                            case R.id.edit_text_presente:
                                mPresente1EditText.setText(null);
                                mPresente2EditText.setText(null);
                                mPresente3EditText.setText(null);
                                mPresente4EditText.setText(null);
                                mPresente5EditText.setText(null);
                                break;
                            case R.id.edit_text_p_p_s:
                                mPps1EditText.setText(null);
                                mPps2EditText.setText(null);
                                mPps3EditText.setText(null);
                                mPps4EditText.setText(null);
                                mPps5EditText.setText(null);
                                break;
                            case R.id.edit_text_p_i_s:
                                mPis1EditText.setText(null);
                                mPis2EditText.setText(null);
                                mPis3EditText.setText(null);
                                mPis4EditText.setText(null);
                                mPis5EditText.setText(null);
                                break;
                            case R.id.edit_text_futuro:
                                mFuturo1EditText.setText(null);
                                mFuturo2EditText.setText(null);
                                mFuturo3EditText.setText(null);
                                mFuturo4EditText.setText(null);
                                mFuturo5EditText.setText(null);
                                break;
                            case R.id.edit_text_p_m_q_p_c:
                                mPmqpc1EditText.setText(null);
                                mPmqpc2EditText.setText(null);
                                mPmqpc3EditText.setText(null);
                                mPmqpc4EditText.setText(null);
                                mPmqpc5EditText.setText(null);
                                break;
                            case R.id.edit_text_p_p_c:
                                mPpc1EditText.setText(null);
                                mPpc2EditText.setText(null);
                                mPpc3EditText.setText(null);
                                mPpc4EditText.setText(null);
                                mPpc5EditText.setText(null);
                                break;
                        }
                        break;
                }
                return true;
            }
        });
    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the verb.
     */
    private void setupSpinner() {
        // Set the integer mSelected to the constant values
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selection = parent.getSelectedItemPosition();
                switch (selection) {
                    case 0:
                        TextView text_presente = (TextView) findViewById(R.id.edit_text_presente);
                        focusOnView(text_presente);
                        break;
                    case 1:
                        TextView text_p_p_s = (TextView) findViewById(R.id.edit_text_p_p_s);
                        focusOnView(text_p_p_s);
                        break;
                    case 2:
                        TextView text_p_i_s = (TextView) findViewById(R.id.edit_text_p_i_s);
                        focusOnView(text_p_i_s);
                        break;
                    case 3:
                        TextView text_futuro = (TextView) findViewById(R.id.edit_text_futuro);
                        focusOnView(text_futuro);
                        break;
                    case 4:
                        TextView text_p_m_q_p_c = (TextView) findViewById(R.id.edit_text_p_m_q_p_c);
                        focusOnView(text_p_m_q_p_c);
                        break;
                    case 5:
                        TextView text_p_p_c = (TextView) findViewById(R.id.edit_text_p_p_c);
                        focusOnView(text_p_p_c);
                        break;
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void focusOnView(final View view) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                ScrollView scroll = (ScrollView) findViewById(R.id.edit_scroll);
                scroll.scrollTo(0, view.getTop());
            }
        });
    }

    private void saveVerb() {
        String porString = mPorVerbEditText.getText().toString().trim();
        String engString = mEngVerbEditText.getText().toString().trim();

        String presente1String = mPresente1EditText.getText().toString().trim();
        String presente2String = mPresente2EditText.getText().toString().trim();
        String presente3String = mPresente3EditText.getText().toString().trim();
        String presente4String = mPresente4EditText.getText().toString().trim();
        String presente5String = mPresente5EditText.getText().toString().trim();
        String pps1String = mPps1EditText.getText().toString().trim();
        String pps2String = mPps2EditText.getText().toString().trim();
        String pps3String = mPps3EditText.getText().toString().trim();
        String pps4String = mPps4EditText.getText().toString().trim();
        String pps5String = mPps5EditText.getText().toString().trim();
        String pis1String = mPis1EditText.getText().toString().trim();
        String pis2String = mPis2EditText.getText().toString().trim();
        String pis3String = mPis3EditText.getText().toString().trim();
        String pis4String = mPis4EditText.getText().toString().trim();
        String pis5String = mPis5EditText.getText().toString().trim();
        String futuro1String = mFuturo1EditText.getText().toString().trim();
        String futuro2String = mFuturo2EditText.getText().toString().trim();
        String futuro3String = mFuturo3EditText.getText().toString().trim();
        String futuro4String = mFuturo4EditText.getText().toString().trim();
        String futuro5String = mFuturo5EditText.getText().toString().trim();
        String pmqpc1String = mPmqpc1EditText.getText().toString().trim();
        String pmqpc2String = mPmqpc2EditText.getText().toString().trim();
        String pmqpc3String = mPmqpc3EditText.getText().toString().trim();
        String pmqpc4String = mPmqpc4EditText.getText().toString().trim();
        String pmqpc5String = mPmqpc5EditText.getText().toString().trim();
        String ppc1String = mPpc1EditText.getText().toString().trim();
        String ppc2String = mPpc2EditText.getText().toString().trim();
        String ppc3String = mPpc3EditText.getText().toString().trim();
        String ppc4String = mPpc4EditText.getText().toString().trim();
        String ppc5String = mPpc5EditText.getText().toString().trim();

        ContentValues values = new ContentValues();
        values.put(COLUMN_VERB_POR, porString);
        values.put(COLUMN_VERB_ENG, engString);

        values.put(COLUMN_PRESENTE_1, presente1String);
        values.put(COLUMN_PRESENTE_2, presente2String);
        values.put(COLUMN_PRESENTE_3, presente3String);
        values.put(COLUMN_PRESENTE_4, presente4String);
        values.put(COLUMN_PRESENTE_5, presente5String);
        values.put(COLUMN_P_P_S_1, pps1String);
        values.put(COLUMN_P_P_S_2, pps2String);
        values.put(COLUMN_P_P_S_3, pps3String);
        values.put(COLUMN_P_P_S_4, pps4String);
        values.put(COLUMN_P_P_S_5, pps5String);
        values.put(COLUMN_P_I_S_1, pis1String);
        values.put(COLUMN_P_I_S_2, pis2String);
        values.put(COLUMN_P_I_S_3, pis3String);
        values.put(COLUMN_P_I_S_4, pis4String);
        values.put(COLUMN_P_I_S_5, pis5String);
        values.put(COLUMN_FUTURO_1, futuro1String);
        values.put(COLUMN_FUTURO_2, futuro2String);
        values.put(COLUMN_FUTURO_3, futuro3String);
        values.put(COLUMN_FUTURO_4, futuro4String);
        values.put(COLUMN_FUTURO_5, futuro5String);
        values.put(COLUMN_P_M_Q_P_C_1, pmqpc1String);
        values.put(COLUMN_P_M_Q_P_C_2, pmqpc2String);
        values.put(COLUMN_P_M_Q_P_C_3, pmqpc3String);
        values.put(COLUMN_P_M_Q_P_C_4, pmqpc4String);
        values.put(COLUMN_P_M_Q_P_C_5, pmqpc5String);
        values.put(COLUMN_P_P_C_1, ppc1String);
        values.put(COLUMN_P_P_C_2, ppc2String);
        values.put(COLUMN_P_P_C_3, ppc3String);
        values.put(COLUMN_P_P_C_4, ppc4String);
        values.put(COLUMN_P_P_C_5, ppc5String);

        if (TextUtils.isEmpty(porString)) {
            // If the new content URI is null, then there was an error with insertion.
            toastView(getString(R.string.add_verb_to_save));
        } else {
            // Determine if this is a new or existing pet by checking if mCurrentPetUri is null or not
            if (mCurrentVerbUri == null) {
                if (isEntered(porString)) {
                    toastView(getString(R.string.verb_is_already_entered_msg));
                } else {
                    // This is a NEW pet, so insert a new pet into the provider,
                    // returning the content URI for the new pet.
                    Uri newUri = getContentResolver().insert(CONTENT_URI, values);

                    // Show a toast message depending on whether or not the insertion was successful.
                    if (newUri == null) {

                        // If the new content URI is null, then there was an error with insertion.
                        toastView(getString(R.string.failed_to_update_verb));
                    } else {
                        // Otherwise, the insertion was successful and we can display a toast.
                        toastView(getString(R.string.successfully_insert_the_verb));
                    }

                }

            } else {
                if (!mVerbHasChanged) {
                    toastView(getString(R.string.no_changes_to_update));
                    finish();
                } else if (isEntered(porString) && porString != porVerb) {
                    toastView(getString(R.string.verb_is_already_entered_msg));
                } else {
                    // Otherwise this is an EXISTING pet, so update the pet with content URI: mCurrentPetUri
                    // and pass in the new ContentValues. Pass in null for the selection and selection args
                    // because mCurrentPetUri will already identify the correct row in the database that
                    // we want to modify.
                    int rowsAffected = getContentResolver().update(mCurrentVerbUri, values, null, null);

                    // Show a toast message depending on whether or not the update was successful.
                    if (rowsAffected == 0) {

                        // If no rows were affected, then there was an error with the update.
                        toastView(getString(R.string.failed_to_update_verb));
                    } else {
                        // Otherwise, the update was successful and we can display a toast.
                        toastView(getString(R.string.successfully_updated_verb));
                    }
                    finish();

                }

            }
        }
    }

    private Boolean isEntered(String verb) {
        DbOpenHelper openHelper = new DbOpenHelper(this);
        SQLiteDatabase database = openHelper.getReadableDatabase();

        String[] query = {COLUMN_VERB_POR};
        String where = COLUMN_VERB_POR + " LIKE " + "'" + verb + "'";
        Cursor c = database.query(VERBS_TABLE_NAME, query, where, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            c.close();
            return true;
        } else {
            return false;
        }
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
     * Perform the deletion of the verb in the database.
     */
    private void deleteVerb() {

        // Otherwise this is an EXISTING verb, so update the verb with content URI: mCurrentVerbUri
        // and pass in the new ContentValues. Pass in null for the selection and selection args
        // because mCurrentVerbUri will already identify the correct row in the database that
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
            case R.id.action_save:
                // save pet to database
                saveVerb();
                // exit editor
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Show a dialog that notifies the user going to delete the pet
                showDeleteConfirmationDialog();
                return true;
            case R.id.action_home:

                // If the pet hasn't changed, continue with navigating up to parent activity
                // which is the {@link CatalogActivity}.
                if (mCurrentVerbUri != null && !mVerbHasChanged) {
                    startActivity(new Intent(EditorActivity.this, MainActivity.class));
                    return true;
                }

                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(EditorActivity.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // If the pet hasn't changed, continue with navigating up to parent activity
                // which is the {@link CatalogActivity}.
                if (mCurrentVerbUri != null && !mVerbHasChanged) {
                    finish();
                    return true;
                }

                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener2 =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                finish();
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener2);
                return true;
            case R.id.action_search:
                startActivity(new Intent(EditorActivity.this, SearchActivity.class));
                return true;
            case R.id.action_fillup:
                if (TextUtils.isEmpty(mPorVerbEditText.getText().toString().trim())) {
                    toastView("Add verb to fill up.");
                } else {
                    String porText = mPorVerbEditText.getText().toString().trim();
                    fillUp(mPresente1EditText, porText);
                    fillUp(mPresente2EditText, porText);
                    fillUp(mPresente3EditText, porText);
                    fillUp(mPresente4EditText, porText);
                    fillUp(mPresente5EditText, porText);
                    fillUp(mPps1EditText, porText);
                    fillUp(mPps2EditText, porText);
                    fillUp(mPps3EditText, porText);
                    fillUp(mPps4EditText, porText);
                    fillUp(mPps5EditText, porText);
                    fillUp(mPis1EditText, porText);
                    fillUp(mPis2EditText, porText);
                    fillUp(mPis3EditText, porText);
                    fillUp(mPis4EditText, porText);
                    fillUp(mPis5EditText, porText);
                    fillUp(mFuturo1EditText, porText);
                    fillUp(mFuturo2EditText, porText);
                    fillUp(mFuturo3EditText, porText);
                    fillUp(mFuturo4EditText, porText);
                    fillUp(mFuturo5EditText, porText);
                    fillUp(mPmqpc1EditText, porText);
                    fillUp(mPmqpc2EditText, porText);
                    fillUp(mPmqpc3EditText, porText);
                    fillUp(mPmqpc4EditText, porText);
                    fillUp(mPmqpc5EditText, porText);
                    fillUp(mPpc1EditText, porText);
                    fillUp(mPpc2EditText, porText);
                    fillUp(mPpc3EditText, porText);
                    fillUp(mPpc4EditText, porText);
                    fillUp(mPpc5EditText, porText);
                }
        }
        return super.onOptionsItemSelected(item);
    }

    public void fillUp(EditText view, String porText) {
        if (TextUtils.isEmpty(view.getText().toString().trim())) {
            if (porText.length() > 2) {
                String type = porText.substring(porText.length() - 2);
                String editedVerb = porText.substring(0, porText.length() - 2);
                if (view == mFuturo1EditText || view == mFuturo2EditText || view == mFuturo3EditText ||
                        view == mFuturo4EditText || view == mFuturo5EditText) {
                    switch (view.getId()) {
                        case R.id.edit_futuro_1:
                            view.setText(porText + "ei");
                            break;
                        case R.id.edit_futuro_2:
                            view.setText(porText + "ás");
                            break;
                        case R.id.edit_futuro_3:
                            view.setText(porText + "á");
                            break;
                        case R.id.edit_futuro_4:
                            view.setText(porText + "emos");
                            break;
                        case R.id.edit_futuro_5:
                            view.setText(porText + "ão");
                            break;
                    }
                } else if (view == mPmqpc1EditText || view == mPmqpc2EditText ||
                        view == mPmqpc3EditText || view == mPmqpc4EditText || view == mPmqpc5EditText ||
                        view == mPpc1EditText || view == mPpc2EditText ||
                        view == mPpc3EditText || view == mPpc4EditText || view == mPpc5EditText) {
                    String pp = null;
                    switch (type) {
                        case "ar":
                            pp = editedVerb + "ado";
                            break;
                        case "er":
                            pp = editedVerb + "ido";
                            break;
                        case "ir":
                            pp = editedVerb + "ido";
                            break;
                    }
                    switch (view.getId()) {
                        case R.id.edit_p_m_q_p_c_1:
                            view.setText("tinha " + pp);
                            break;
                        case R.id.edit_p_m_q_p_c_2:
                            view.setText("tinhas " + pp);
                            break;
                        case R.id.edit_p_m_q_p_c_3:
                            view.setText("tinha " + pp);
                            break;
                        case R.id.edit_p_m_q_p_c_4:
                            view.setText("tínhamos " + pp);
                            break;
                        case R.id.edit_p_m_q_p_c_5:
                            view.setText("tinham " + pp);
                            break;
                        case R.id.edit_p_p_c_1:
                            view.setText("tenho " + pp);
                            break;
                        case R.id.edit_p_p_c_2:
                            view.setText("tens " + pp);
                            break;
                        case R.id.edit_p_p_c_3:
                            view.setText("tem " + pp);
                            break;
                        case R.id.edit_p_p_c_4:
                            view.setText("temos " + pp);
                            break;
                        case R.id.edit_p_p_c_5:
                            view.setText("têm " + pp);
                            break;
                    }
                } else {
                    switch (type) {
                        case "ar":
                            switch (view.getId()) {
                                case R.id.edit_presente_1:
                                    view.setText(editedVerb + "o");
                                    break;
                                case R.id.edit_presente_2:
                                    view.setText(editedVerb + "as");
                                    break;
                                case R.id.edit_presente_3:
                                    view.setText(editedVerb + "a");
                                    break;
                                case R.id.edit_presente_4:
                                    view.setText(editedVerb + "amos");
                                    break;
                                case R.id.edit_presente_5:
                                    view.setText(editedVerb + "am");
                                    break;
                                case R.id.edit_p_p_s_1:
                                    view.setText(editedVerb + "ei");
                                    break;
                                case R.id.edit_p_p_s_2:
                                    view.setText(editedVerb + "aste");
                                    break;
                                case R.id.edit_p_p_s_3:
                                    view.setText(editedVerb + "ou");
                                    break;
                                case R.id.edit_p_p_s_4:
                                    view.setText(editedVerb + "ámos");
                                    break;
                                case R.id.edit_p_p_s_5:
                                    view.setText(editedVerb + "aram");
                                    break;
                                case R.id.edit_p_i_s_1:
                                    view.setText(editedVerb + "ava");
                                    break;
                                case R.id.edit_p_i_s_2:
                                    view.setText(editedVerb + "avas");
                                    break;
                                case R.id.edit_p_i_s_3:
                                    view.setText(editedVerb + "ava");
                                    break;
                                case R.id.edit_p_i_s_4:
                                    view.setText(editedVerb + "ávamos");
                                    break;
                                case R.id.edit_p_i_s_5:
                                    view.setText(editedVerb + "avam");
                                    break;
                            }
                        case "er":
                            switch (view.getId()) {
                                case R.id.edit_presente_1:
                                    view.setText(editedVerb + "o");
                                    break;
                                case R.id.edit_presente_2:
                                    view.setText(editedVerb + "es");
                                    break;
                                case R.id.edit_presente_3:
                                    view.setText(editedVerb + "e");
                                    break;
                                case R.id.edit_presente_4:
                                    view.setText(editedVerb + "emos");
                                    break;
                                case R.id.edit_presente_5:
                                    view.setText(editedVerb + "em");
                                    break;
                                case R.id.edit_p_p_s_1:
                                    view.setText(editedVerb + "i");
                                    break;
                                case R.id.edit_p_p_s_2:
                                    view.setText(editedVerb + "este");
                                    break;
                                case R.id.edit_p_p_s_3:
                                    view.setText(editedVerb + "eu");
                                    break;
                                case R.id.edit_p_p_s_4:
                                    view.setText(editedVerb + "emos");
                                    break;
                                case R.id.edit_p_p_s_5:
                                    view.setText(editedVerb + "eram");
                                    break;
                                case R.id.edit_p_i_s_1:
                                    view.setText(editedVerb + "ia");
                                    break;
                                case R.id.edit_p_i_s_2:
                                    view.setText(editedVerb + "ias");
                                    break;
                                case R.id.edit_p_i_s_3:
                                    view.setText(editedVerb + "ia");
                                    break;
                                case R.id.edit_p_i_s_4:
                                    view.setText(editedVerb + "íamos");
                                    break;
                                case R.id.edit_p_i_s_5:
                                    view.setText(editedVerb + "iam");
                                    break;
                            }
                        case "ir":
                            switch (view.getId()) {
                                case R.id.edit_presente_1:
                                    view.setText(editedVerb + "o");
                                    break;
                                case R.id.edit_presente_2:
                                    view.setText(editedVerb + "es");
                                    break;
                                case R.id.edit_presente_3:
                                    view.setText(editedVerb + "e");
                                    break;
                                case R.id.edit_presente_4:
                                    view.setText(editedVerb + "imos");
                                    break;
                                case R.id.edit_presente_5:
                                    view.setText(editedVerb + "em");
                                    break;
                                case R.id.edit_p_p_s_1:
                                    view.setText(editedVerb + "i");
                                    break;
                                case R.id.edit_p_p_s_2:
                                    view.setText(editedVerb + "iste");
                                    break;
                                case R.id.edit_p_p_s_3:
                                    view.setText(editedVerb + "iu");
                                    break;
                                case R.id.edit_p_p_s_4:
                                    view.setText(editedVerb + "imos");
                                    break;
                                case R.id.edit_p_p_s_5:
                                    view.setText(editedVerb + "iram");
                                    break;
                                case R.id.edit_p_i_s_1:
                                    view.setText(editedVerb + "ia");
                                    break;
                                case R.id.edit_p_i_s_2:
                                    view.setText(editedVerb + "ias");
                                    break;
                                case R.id.edit_p_i_s_3:
                                    view.setText(editedVerb + "ia");
                                    break;
                                case R.id.edit_p_i_s_4:
                                    view.setText(editedVerb + "íamos");
                                    break;
                                case R.id.edit_p_i_s_5:
                                    view.setText(editedVerb + "iam");
                                    break;
                            }
                    }

                }
            }
        }}


        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            // Define a projection that specifies the columns from the table we care about.
            String[] projection = {
                    _ID,
                    COLUMN_VERB_POR,
                    COLUMN_VERB_ENG,
                    COLUMN_PRESENTE_1,
                    COLUMN_PRESENTE_2,
                    COLUMN_PRESENTE_3,
                    COLUMN_PRESENTE_4,
                    COLUMN_PRESENTE_5,
                    COLUMN_P_P_S_1,
                    COLUMN_P_P_S_2,
                    COLUMN_P_P_S_3,
                    COLUMN_P_P_S_4,
                    COLUMN_P_P_S_5,
                    COLUMN_P_I_S_1,
                    COLUMN_P_I_S_2,
                    COLUMN_P_I_S_3,
                    COLUMN_P_I_S_4,
                    COLUMN_P_I_S_5,
                    COLUMN_FUTURO_1,
                    COLUMN_FUTURO_2,
                    COLUMN_FUTURO_3,
                    COLUMN_FUTURO_4,
                    COLUMN_FUTURO_5,
                    COLUMN_FUTURO_1,
                    COLUMN_P_M_Q_P_C_1,
                    COLUMN_P_M_Q_P_C_2,
                    COLUMN_P_M_Q_P_C_3,
                    COLUMN_P_M_Q_P_C_4,
                    COLUMN_P_M_Q_P_C_5,
                    COLUMN_P_P_C_1,
                    COLUMN_P_P_C_2,
                    COLUMN_P_P_C_3,
                    COLUMN_P_P_C_4,
                    COLUMN_P_P_C_5};

            // This loader will execute the ContentProvider's query method on a background thread
            return new CursorLoader(this,   // Parent activity context
                    mCurrentVerbUri,   // Provider content URI to query
                    projection,             // Columns to include in the resulting Cursor
                    null,                   // No selection clause
                    null,                   // No selection arguments
                    null);                  // Default sort order
        }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if (cursor.moveToFirst()) {
            // Find the columns of pet attributes that we're interested in
            int porColumnIndex = cursor.getColumnIndex(COLUMN_VERB_POR);
            int engColumnIndex = cursor.getColumnIndex(COLUMN_VERB_ENG);
            int presente1ColumnIndex = cursor.getColumnIndex(COLUMN_PRESENTE_1);
            int presente2ColumnIndex = cursor.getColumnIndex(COLUMN_PRESENTE_2);
            int presente3ColumnIndex = cursor.getColumnIndex(COLUMN_PRESENTE_3);
            int presente4ColumnIndex = cursor.getColumnIndex(COLUMN_PRESENTE_4);
            int presente5ColumnIndex = cursor.getColumnIndex(COLUMN_PRESENTE_5);
            int pps1ColumnIndex = cursor.getColumnIndex(COLUMN_P_P_S_1);
            int pps2ColumnIndex = cursor.getColumnIndex(COLUMN_P_P_S_2);
            int pps3ColumnIndex = cursor.getColumnIndex(COLUMN_P_P_S_3);
            int pps4ColumnIndex = cursor.getColumnIndex(COLUMN_P_P_S_4);
            int pps5ColumnIndex = cursor.getColumnIndex(COLUMN_P_P_S_5);
            int pis1ColumnIndex = cursor.getColumnIndex(COLUMN_P_I_S_1);
            int pis2ColumnIndex = cursor.getColumnIndex(COLUMN_P_I_S_2);
            int pis3ColumnIndex = cursor.getColumnIndex(COLUMN_P_I_S_3);
            int pis4ColumnIndex = cursor.getColumnIndex(COLUMN_P_I_S_4);
            int pis5ColumnIndex = cursor.getColumnIndex(COLUMN_P_I_S_5);
            int futuro1ColumnIndex = cursor.getColumnIndex(COLUMN_FUTURO_1);
            int futuro2ColumnIndex = cursor.getColumnIndex(COLUMN_FUTURO_2);
            int futuro3ColumnIndex = cursor.getColumnIndex(COLUMN_FUTURO_3);
            int futuro4ColumnIndex = cursor.getColumnIndex(COLUMN_FUTURO_4);
            int futuro5ColumnIndex = cursor.getColumnIndex(COLUMN_FUTURO_5);
            int pmqpc1ColumnIndex = cursor.getColumnIndex(COLUMN_P_M_Q_P_C_1);
            int pmqpc2ColumnIndex = cursor.getColumnIndex(COLUMN_P_M_Q_P_C_2);
            int pmqpc3ColumnIndex = cursor.getColumnIndex(COLUMN_P_M_Q_P_C_3);
            int pmqpc4ColumnIndex = cursor.getColumnIndex(COLUMN_P_M_Q_P_C_4);
            int pmqpc5ColumnIndex = cursor.getColumnIndex(COLUMN_P_M_Q_P_C_5);
            int ppc1ColumnIndex = cursor.getColumnIndex(COLUMN_P_P_C_1);
            int ppc2ColumnIndex = cursor.getColumnIndex(COLUMN_P_P_C_2);
            int ppc3ColumnIndex = cursor.getColumnIndex(COLUMN_P_P_C_3);
            int ppc4ColumnIndex = cursor.getColumnIndex(COLUMN_P_P_C_4);
            int ppc5ColumnIndex = cursor.getColumnIndex(COLUMN_P_P_C_5);

            // Extract out the value from the Cursor for the given column index
            porVerb = cursor.getString(porColumnIndex);
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
            String pmqpc1Verb= cursor.getString(pmqpc1ColumnIndex);
            String pmqpc2Verb= cursor.getString(pmqpc2ColumnIndex);
            String pmqpc3Verb= cursor.getString(pmqpc3ColumnIndex);
            String pmqpc4Verb= cursor.getString(pmqpc4ColumnIndex);
            String pmqpc5Verb= cursor.getString(pmqpc5ColumnIndex);
            String ppc1Verb = cursor.getString(ppc1ColumnIndex);
            String ppc2Verb = cursor.getString(ppc2ColumnIndex);
            String ppc3Verb = cursor.getString(ppc3ColumnIndex);
            String ppc4Verb = cursor.getString(ppc4ColumnIndex);
            String ppc5Verb = cursor.getString(ppc5ColumnIndex);

            // Update the views on the screen with the values from the database
            mPorVerbEditText.setText(porVerb);
            mEngVerbEditText.setText(engVerb);

            mPresente1EditText.setText(presente1Verb);
            mPresente2EditText.setText(presente2Verb);
            mPresente3EditText.setText(presente3Verb);
            mPresente4EditText.setText(presente4Verb);
            mPresente5EditText.setText(presente5Verb);
            mPps1EditText.setText(pps1Verb);
            mPps2EditText.setText(pps2Verb);
            mPps3EditText.setText(pps3Verb);
            mPps4EditText.setText(pps4Verb);
            mPps5EditText.setText(pps5Verb);
            mPis1EditText.setText(pis1Verb);
            mPis2EditText.setText(pis2Verb);
            mPis3EditText.setText(pis3Verb);
            mPis4EditText.setText(pis4Verb);
            mPis5EditText.setText(pis5Verb);
            mFuturo1EditText.setText(futuro1Verb);
            mFuturo2EditText.setText(futuro2Verb);
            mFuturo3EditText.setText(futuro3Verb);
            mFuturo4EditText.setText(futuro4Verb);
            mFuturo5EditText.setText(futuro5Verb);
            mPmqpc1EditText.setText(pmqpc1Verb);
            mPmqpc2EditText.setText(pmqpc2Verb);
            mPmqpc3EditText.setText(pmqpc3Verb);
            mPmqpc4EditText.setText(pmqpc4Verb);
            mPmqpc5EditText.setText(pmqpc5Verb);
            mPpc1EditText.setText(ppc1Verb);
            mPpc2EditText.setText(ppc2Verb);
            mPpc3EditText.setText(ppc3Verb);
            mPpc4EditText.setText(ppc4Verb);
            mPpc5EditText.setText(ppc5Verb);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mVerbHasChanged = true;
            return false;
        }
    };

        /**
         * Show a dialog that warns the user there are unsaved changes that will be lost
         * if they continue leaving the editor.
         *
         * @param discardButtonClickListener is the click listener for what to do when
         *                                   the user confirms they want to discard their changes
         */

        private void showUnsavedChangesDialog(
                DialogInterface.OnClickListener discardButtonClickListener) {
            // Create an AlertDialog.Builder and set the message, and click listeners
            // for the positive and negative buttons on the dialog.
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.unsaved_changes_msg);
            builder.setPositiveButton(R.string.leave_without_saving, discardButtonClickListener);
            builder.setNegativeButton(R.string.save, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked the "Keep editing" button, so dismiss the dialog
                    // and continue editing the pet.
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    saveVerb();
                }
            });

            // Create and show the AlertDialog
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

    /**
     * This method is called when the back button is pressed.
     */
    @Override
    public void onBackPressed() {
        // If the pet hasn't changed, continue with handling back button press
        if (mCurrentVerbUri != null && !mVerbHasChanged) {
            super.onBackPressed();
            return;
        }

        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that changes should be discarded.
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, close the current activity.
                        finish();
                    }
                };

        // Show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu can be updated (some menu items can be hidden or made visible).
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new pet, hide the "Delete" menu item.
        if (mCurrentVerbUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        MenuItem menuItemEdit = menu.findItem(R.id.action_edit);
        menuItemEdit.setVisible(false);
        return true;
    }

    private void toastView(String msg) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
