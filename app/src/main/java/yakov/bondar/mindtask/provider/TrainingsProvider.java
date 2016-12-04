package yakov.bondar.mindtask.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;


public class TrainingsProvider extends ContentProvider {

    private final String TAG = "tag";

    private static final String DB_NAME = "trainingsDb";
    private static final int DB_VERSION = 1;
    private static final String TRAININGS_TABLE = "trainings";
    private static final String TRAINING_ID = "_id";
    public static final String TRAINING_DAY = "day";
    public static final String TRAINING_CATEGORY_ID = "category_id";
    public static final String TRAINING_CATEGORY_NAME = "category_name";

    private static final String DB_CREATE = "create table " + TRAININGS_TABLE + "("
            + TRAINING_ID + " integer primary key autoincrement, "
            + TRAINING_DAY + " text, "
            + TRAINING_CATEGORY_ID + " integer, "
            + TRAINING_CATEGORY_NAME + " text" + ");";

    private static final String AUTHORITY = "yakov.bondar.mindtask.TrainingProvider";

    private static final String TRAINING_PATH = "trainings";

    public static final Uri TRAINING_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + TRAINING_PATH);

    //типы данных
    //набор строк
    private static final String TRAINING_CONTENT_TYPE = "vnd.android.cursor.dir/vnd."
            + AUTHORITY + "." + TRAINING_PATH;
    //одна строка
    private static final String TRAINING_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."
            + AUTHORITY + "." + TRAINING_PATH;

    public static final int URI_TRAININGS = 1;
    public static final int URI_TRAININGS_ID = 2;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, TRAINING_PATH, URI_TRAININGS);
        uriMatcher.addURI(AUTHORITY, TRAINING_PATH, URI_TRAININGS_ID);
    }

    private DbHelper mDbHelper;
    private SQLiteDatabase mDb;

    @Override
    public boolean onCreate() {
        Log.d(TAG, "TrainingsProvider.onCreate");
        mDbHelper = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "query, " + uri.toString());
        //check URI
        switch (uriMatcher.match(uri)) {
            case URI_TRAININGS:
                sortOrder = TRAINING_DAY + " ASC";
                break;
            case URI_TRAININGS_ID:
                String trainingDay = uri.getLastPathSegment();
                Log.d(TAG, "query: URI_CONTACTS_ID, " + trainingDay);
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        mDb = mDbHelper.getWritableDatabase();
        Cursor cursor = mDb.query(TRAININGS_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), TRAINING_CONTENT_URI);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        Log.d(TAG, "getType, " + uri.toString());
        switch (uriMatcher.match(uri)) {
            case URI_TRAININGS:
                return TRAINING_CONTENT_TYPE;
            case URI_TRAININGS_ID:
                return TRAINING_CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "insert, " + uri.toString());
        mDb = mDbHelper.getWritableDatabase();
        long rowId = mDb.insert(TRAININGS_TABLE, null, values);
        Uri resultUri = ContentUris.withAppendedId(TRAINING_CONTENT_URI, rowId);
        getContext().getContentResolver().notifyChange(resultUri, null);
        return resultUri;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "delete, " + uri.toString());
        switch (uriMatcher.match(uri)) {
            case URI_TRAININGS:
                Log.d(TAG, "URI_TRAININGS");
                break;
            case URI_TRAININGS_ID:
                String id = uri.getLastPathSegment();
                Log.d(TAG, "URI_TRAININGS_ID, " + id);
                if (TextUtils.isEmpty(selection)) {
                    selection = TRAINING_ID + " = " + id;
                } else {
                    selection = selection + " AND " + TRAINING_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        mDb = mDbHelper.getWritableDatabase();
        int cnt = mDb.delete(TRAININGS_TABLE, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }

    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    private class DbHelper extends SQLiteOpenHelper {

        private final String DROP_TABLE = "DROP TABLE IF EXISTS " + TRAININGS_TABLE;

        public DbHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
            /*ContentValues cv = new ContentValues();
            for (int i = 0; i < Day.values().length; i++) {
                cv.put(TRAINING_DAY, Day.values()[i].name());
                cv.put(TRAINING_CATEGORY_ID, -1);
                cv.put(TRAINING_CATEGORY_NAME, "");
                db.insert(TRAININGS_TABLE, null, cv);
            }*/
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }
    }
}
