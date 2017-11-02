package com.example.dilkidias.gramatica;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.dilkidias.gramatica.Data.DataContract;
import com.example.dilkidias.gramatica.Data.DataContract.DataEntry;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.breadCrumbShortTitle;
import static android.R.attr.filter;
import static android.view.View.GONE;

/**
* Created by DILKI DIAS on 04-Jun-17.
*/

public class VerbCursorAdapter extends CursorAdapter {

    public VerbCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.verb_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView porText = (TextView) view.findViewById(R.id.verb_por);
        TextView engText = (TextView) view.findViewById(R.id.verb_eng);

        int porColumnIndex;
        int engColumnIndex;

        String currentPor = null;
        String currentEng;

        String activity = context.getClass().getSimpleName();

        switch (activity) {
            case "MainActivity":
                switch (cursor.getColumnCount()){
                    case DataEntry.DEFAULT_NUM_COLUMNS_VERBS:
                        porColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_VERB_POR);
                        engColumnIndex = cursor.getColumnIndex(DataEntry.COLUMN_VERB_ENG);

                        currentPor = cursor.getString(porColumnIndex);
                        currentEng = cursor.getString(engColumnIndex);

                        // If the pet breed is empty string or null, then use some default text
                        // that says "Unknown breed", so the TextView isn't blank.
                        if (TextUtils.isEmpty(currentEng)) {
                            currentEng = context.getString(R.string.no_meaning);
                        }
                        engText.setText(currentEng);
                        break;
                    case DataEntry.DEFAULT_NUM_COLUMNS_TENSES:
                        porColumnIndex = cursor.getColumnIndex(DataEntry.COLUMN_TENSE_NAME);
                        currentPor = cursor.getString(porColumnIndex);
                        engText.setVisibility(GONE);
                        break;
                }
                porText.setText(currentPor);
                break;
            case "SearchActivity":
                String search = SearchActivity.searchWord;

                ArrayList<String> columns = new ArrayList<String>();
                switch (cursor.getColumnCount()) {
                    case DataEntry.DEFAULT_NUM_COLUMNS_VERBS:
                        columns.add(DataEntry.COLUMN_VERB_POR);
                        columns.add(DataEntry.COLUMN_VERB_ENG);
                        columns.add(DataEntry.COLUMN_PRESENTE_1);
                        columns.add(DataEntry.COLUMN_PRESENTE_2);
                        columns.add(DataEntry.COLUMN_PRESENTE_3);
                        columns.add(DataEntry.COLUMN_PRESENTE_4);
                        columns.add(DataEntry.COLUMN_PRESENTE_5);
                        columns.add(DataEntry.COLUMN_P_P_S_1);
                        columns.add(DataEntry.COLUMN_P_P_S_2);
                        columns.add(DataEntry.COLUMN_P_P_S_3);
                        columns.add(DataEntry.COLUMN_P_P_S_4);
                        columns.add(DataEntry.COLUMN_P_P_S_5);
                        columns.add(DataEntry.COLUMN_P_I_S_1);
                        columns.add(DataEntry.COLUMN_P_I_S_2);
                        columns.add(DataEntry.COLUMN_P_I_S_3);
                        columns.add(DataEntry.COLUMN_P_I_S_4);
                        columns.add(DataEntry.COLUMN_P_I_S_5);
                        columns.add(DataEntry.COLUMN_FUTURO_1);
                        columns.add(DataEntry.COLUMN_FUTURO_2);
                        columns.add(DataEntry.COLUMN_FUTURO_3);
                        columns.add(DataEntry.COLUMN_FUTURO_4);
                        columns.add(DataEntry.COLUMN_FUTURO_5);
                        columns.add(DataEntry.COLUMN_P_M_Q_P_C_1);
                        columns.add(DataEntry.COLUMN_P_M_Q_P_C_2);
                        columns.add(DataEntry.COLUMN_P_M_Q_P_C_3);
                        columns.add(DataEntry.COLUMN_P_M_Q_P_C_4);
                        columns.add(DataEntry.COLUMN_P_M_Q_P_C_5);
                        columns.add(DataEntry.COLUMN_P_P_C_1);
                        columns.add(DataEntry.COLUMN_P_P_C_2);
                        columns.add(DataEntry.COLUMN_P_P_C_3);
                        columns.add(DataEntry.COLUMN_P_P_C_4);
                        columns.add(DataEntry.COLUMN_P_P_C_5);

                        porColumnIndex = cursor.getColumnIndex(DataEntry.COLUMN_VERB_POR);
                        engColumnIndex = cursor.getColumnIndex(DataEntry.COLUMN_VERB_ENG);

                        currentPor = cursor.getString(porColumnIndex);
                        currentEng = cursor.getString(engColumnIndex);

                        if (TextUtils.isEmpty(currentEng)) {
                            currentEng = context.getString(R.string.no_meaning);
                        }
                        porText.setText(currentPor + " (" + currentEng + ")");
                    break;
                    case DataEntry.DEFAULT_NUM_COLUMNS_TENSES:
                        columns.add(DataEntry.COLUMN_TENSE_NAME);

                        int nameColumnIndex = cursor.getColumnIndex(DataEntry.COLUMN_TENSE_NAME);
                        currentPor = cursor.getString(nameColumnIndex);

                        porText.setText(currentPor);
                    break;
                }

                ArrayList<String> selectedStrings = new ArrayList<String>();
                for (int i = 0; i < columns.size(); i++){
                    String word = cursor.getString(cursor.getColumnIndex(columns.get(i)));
                    if (word != null && !TextUtils.isEmpty(word) && word.contains(search)){
                        selectedStrings.add(word);
                    }
                }

//                String selectedStrings = columns.indices
//                        .map { columns[it] }
//                        .map { cursor.getColumnIndex(it) }
//                        .map { cursor.getString(it) }
//                        .filter { !TextUtils.isEmpty(it) && it.contains(search.toString()) }

                if (!selectedStrings.isEmpty()) {
                    String print = selectedStrings.get(0);
                    for (int x = 1; x < selectedStrings.size(); x++) {
                        print += ", " + selectedStrings.get(x);
                    }

                    engText.setText(print);
            }
        }

    }
}
