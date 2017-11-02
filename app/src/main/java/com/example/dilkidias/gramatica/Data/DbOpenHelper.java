package com.example.dilkidias.gramatica.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
* Created by DILKI DIAS on 31-Jul-17.
*/

public class DbOpenHelper extends SQLiteAssetHelper {

        private static final String DATABASE_NAME = "grametica.db";
        private static final int DATABASE_VERSION = 1;

        public DbOpenHelper(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
}
