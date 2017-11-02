package com.example.dilkidias.gramatica.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;

import static com.example.dilkidias.gramatica.Data.DataContract.*;
import static com.example.dilkidias.gramatica.Data.DataContract.CONTENT_AUTHORITY;
import static com.example.dilkidias.gramatica.Data.DataContract.PATH_VERBS;

/**
 * Created by DILKI DIAS on 04-Jun-17.
 */

public class DataProvider extends ContentProvider {

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = DataProvider.class.getSimpleName();
    /**
     * URI matcher code for the content URI for the pets table
     */
    private static final int VERBS = 100;
    private static final int TENSE = 200;
    /**
     * URI matcher code for the content URI for a single pet in the pets table
     */
    private static final int VERBS_ID = 101;
    private static final int TENSE_ID = 201;
    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {

        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_VERBS, VERBS);
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_VERBS + "/#", VERBS_ID);
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_TENSES, TENSE);
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_TENSES + "/#", TENSE_ID);

    }

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private Long last_insert_id = DataEntry.DEFAULT_NUM_VERBS;

    private DataProvider instance  = null;

    @Override
    public boolean onCreate() {
        this.openHelper = new DbOpenHelper(getContext());
        // Make sure the variable is a global variable, so it can be referenced from other
        // ContentProvider methods.
        if (instance == null) {
            instance = new DataProvider();
        }
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        // Get readable database
        this.database = openHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);

        switch (match) {
            case VERBS:
                // For the PETS code, query the pets table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the pets table.

                // This will perform a query on the pets table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(DataEntry.VERBS_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case TENSE:
                cursor = database.query(DataEntry.TENSES_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case VERBS_ID:

                // For the PET_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.pets/pets/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = DataEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                // This will perform a query on the pets table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(DataEntry.VERBS_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case TENSE_ID:
                selection = DataEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                // This will perform a query on the pets table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(DataEntry.TENSES_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case VERBS:
                return DataEntry.VERBS_CONTENT_LIST_TYPE;
            case VERBS_ID:
                return DataEntry.VERBS_CONTENT_LIST_TYPE;
            case TENSE:
                return DataEntry.CONTENT_LIST_TYPE;
            case TENSE_ID:
                return DataEntry.CONTENT_LIST_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        Long lastId = lastIndex();
        switch (match) {
            case VERBS:
                contentValues.put("_id", lastId + 1);
                // Check that the verb_por is not null
                String verb_por = contentValues.getAsString(DataEntry.COLUMN_VERB_POR);
                if (verb_por == null) {
                    throw new IllegalArgumentException("Verb requires.");
                } else if (isEntered(verb_por)) {
                    throw new IllegalArgumentException("This verb already entered.");
                } else {
                    insertVerb(uri, contentValues);
                }
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }

    }

    private Long lastIndex() {
        Long lastId = last_insert_id + 1;
        String[] query = {"MAX(id)"};
        Cursor c = database.query(DataEntry.VERBS_TABLE_NAME, query, null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            lastId = c.getLong(0);
            c.close();
        }
        return lastId;
    }


    private Uri insertVerb(Uri uri, ContentValues values) {

        final int match = sUriMatcher.match(uri);

        // Get writeable database
        this.database = openHelper.getWritableDatabase();

        // Insert the new data with the given values
        Long id = database.insert(DataEntry.VERBS_TABLE_NAME, null, values);

        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            return null;
        } else {
            last_insert_id = id;
        }

        // Notify all listeners that the data has changed for the data content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    private Boolean isEntered(String por) {

        String[] query = {DataEntry.COLUMN_VERB_POR};
        String where = DataEntry.COLUMN_VERB_POR + " LIKE " + "'" + por + "'";
        Cursor c = database.query(DataEntry.VERBS_TABLE_NAME, query, where, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            c.close();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        // Get writable database
        this.database = openHelper.getWritableDatabase();

        // Track the number of rows that were deleted
        int rowsDeleted;

        getContext().getContentResolver().notifyChange(uri, null);

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case VERBS:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(DataEntry.VERBS_TABLE_NAME, selection, selectionArgs);
                break;
            case VERBS_ID:
                // Delete a single row given by the ID in the URI
                selection = DataEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                rowsDeleted = database.delete(DataEntry.VERBS_TABLE_NAME, selection, selectionArgs);

            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {

        final int match = sUriMatcher.match(uri);

        String name = contentValues.getAsString(DataEntry.COLUMN_VERB_POR);
        switch (match) {
            case VERBS:
                return updateVerb(uri, contentValues, selection, selectionArgs);
            case VERBS_ID:
                if (contentValues.containsKey(DataEntry.COLUMN_VERB_POR)) {
                    if (name == null) {
                        throw new IllegalArgumentException("Verb requires a name");
                    }
                }
                // For the PET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = DataEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateVerb(uri, contentValues, selection, selectionArgs);

            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateVerb(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        // If the {@link PetEntry#COLUMN_PET_NAME} key is present,
        // check that the name value is not null.
        if (values.containsKey(DataEntry.COLUMN_VERB_POR)) {
            String name = values.getAsString(DataEntry.COLUMN_VERB_POR);

            if (name == null) {
                throw new IllegalArgumentException("Verb requires a name");
            }
        }

        // No need to check the breed, any value is valid (including null).
        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        this.database = openHelper.getWritableDatabase();

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(DataEntry.VERBS_TABLE_NAME, values, selection, selectionArgs);

        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        // Return the number of rows updated
        return rowsUpdated;
    }
}
