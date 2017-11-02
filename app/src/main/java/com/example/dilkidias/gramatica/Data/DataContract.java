package com.example.dilkidias.gramatica.Data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
* Created by DILKI DIAS on 04-Jun-17.
*/

public class DataContract {

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.example.dilkidias.gramatica";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.example.android.data/data/ is a valid path for
     * looking at data data. content://com.example.android.data/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_VERBS = "verbs";
    public static final String PATH_TENSES = "tenses";

    public static class DataEntry implements BaseColumns {

            public static final String VERBS_TABLE_NAME = "verbs";

            public static final String _ID = "id";
            public static final String COLUMN_VERB_POR = "por";
            public static final String COLUMN_VERB_ENG = "eng";
            public static final String COLUMN_PRESENTE_1 = "presente_1";
            public static final String COLUMN_PRESENTE_2 = "presente_2";
            public static final String COLUMN_PRESENTE_3 = "presente_3";
            public static final String COLUMN_PRESENTE_4 = "presente_4";
            public static final String COLUMN_PRESENTE_5 = "presente_5";
            public static final String COLUMN_P_P_S_1 = "P_P_S_1";
            public static final String COLUMN_P_P_S_2 = "P_P_S_2";
            public static final String COLUMN_P_P_S_3 = "P_P_S_3";
            public static final String COLUMN_P_P_S_4 = "P_P_S_4";
            public static final String COLUMN_P_P_S_5 = "P_P_S_5";
            public static final String COLUMN_P_I_S_1 = "P_I_S_1";
            public static final String COLUMN_P_I_S_2 = "P_I_S_2";
            public static final String COLUMN_P_I_S_3 = "P_I_S_3";
            public static final String COLUMN_P_I_S_4 = "P_I_S_4";
            public static final String COLUMN_P_I_S_5 = "P_I_S_5";
            public static final String COLUMN_FUTURO_1 = "futuro_1";
            public static final String COLUMN_FUTURO_2 = "futuro_2";
            public static final String COLUMN_FUTURO_3 = "futuro_3";
            public static final String COLUMN_FUTURO_4 = "futuro_4";
            public static final String COLUMN_FUTURO_5 = "futuro_5";
            public static final String COLUMN_P_M_Q_P_C_1 = "P_M_Q_P_C_1";
            public static final String COLUMN_P_M_Q_P_C_2 = "P_M_Q_P_C_2";
            public static final String COLUMN_P_M_Q_P_C_3 = "P_M_Q_P_C_3";
            public static final String COLUMN_P_M_Q_P_C_4 = "P_M_Q_P_C_4";
            public static final String COLUMN_P_M_Q_P_C_5 = "P_M_Q_P_C_5";
            public static final String COLUMN_P_P_C_1 = "P_P_C_1";
            public static final String COLUMN_P_P_C_2 = "P_P_C_2";
            public static final String COLUMN_P_P_C_3 = "P_P_C_3";
            public static final String COLUMN_P_P_C_4 = "P_P_C_4";
            public static final String COLUMN_P_P_C_5 = "P_P_C_5";

            /**
             * The content URI to access the data data in the provider
             */
            public static final Uri VERBS_CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_VERBS);

            /**
             * The MIME type of the [.VERBS_CONTENT_URI] for a list of data.
             */
            public static final String VERBS_CONTENT_LIST_TYPE =
                    ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VERBS;

            /**
             * The MIME type of the [.VERBS_CONTENT_URI] for a single data.
             */
            public static final String VERBS_CONTENT_ITEM_TYPE =
                    ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VERBS;

        public static final Long DEFAULT_NUM_VERBS = 47L;
        public static final int DEFAULT_NUM_COLUMNS_VERBS = 44;

            public static final String TENSES_TABLE_NAME = "tenses";

            public static final String TENSES_ID = "id";
            public static final String COLUMN_TENSE_NAME = "name";
            public static final String COLUMN_TENSE_DETAILS = "details";
            public static final String COLUMN_TENSE_TABLE = "table";
            public static final String COLUMN_TENSE_AR_EU = "ar_eu";
            public static final String COLUMN_TENSE_AR_TU = "ar_tu";
            public static final String COLUMN_TENSE_AR_ELE = "ar_ele";
            public static final String COLUMN_TENSE_AR_NOS = "ar_nos";
            public static final String COLUMN_TENSE_AR_ELES = "ar_eles";
            public static final String COLUMN_TENSE_ER_EU = "er_eu";
            public static final String COLUMN_TENSE_ER_TU = "er_tu";
            public static final String COLUMN_TENSE_ER_ELE = "er_ele";
            public static final String COLUMN_TENSE_ER_NOS = "er_nos";
            public static final String COLUMN_TENSE_ER_ELES = "er_eles";
            public static final String COLUMN_TENSE_IR_EU = "ir_eu";
            public static final String COLUMN_TENSE_IR_TU = "ir_tu";
            public static final String COLUMN_TENSE_IR_ELE = "ir_ele";
            public static final String COLUMN_TENSE_IR_NOS = "ir_nos";
            public static final String COLUMN_TENSE_IR_ELES = "ir_eles";
            public static final String COLUMN_NUM_IRR_VERBS = "num_irr_verbs";
            public static final String COLUMN_IRR = "irr_verbs";

            /**
             * The content URI to access the data data in the provider
             */
            public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TENSES);

            /**
             * The MIME type of the [.CONTENT_URI] for a list of data.
             */
            public static final String CONTENT_LIST_TYPE =
                    ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TENSES;

            /**
             * The MIME type of the [.CONTENT_URI] for a single data.
             */
            public static final String CONTENT_ITEM_TYPE =
                    ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TENSES;

        public static final int DEFAULT_NUM_COLUMNS_TENSES = 22;


    }
}// To prevent someone from accidentally instantiating the contract class,
// give it an empty constructor.