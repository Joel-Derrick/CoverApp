package joel.derrick.coverapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by JD on 07/10/17.
 */

public class StoreDatabaseHelper extends SQLiteOpenHelper {

    // Database Name
    public static String DATABASE_NAME = "store_inventory_database.db";

    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_ITEMS = "Items";

    private static final String KEY_ID = "id";
    private static final String KEY_ITEM_NAME = "item_name";
    private static final String KEY_ITEM_DESC = "item_desc";
    private static final String KEY_PRICE = "price";
    private static final String KEY_WEIGHT = "weight";

    public static String TAG = "tag";

    //SQL string to create the default table
    private static final String CREATE_TABLE_ITEMS = "CREATE TABLE "
            + TABLE_ITEMS + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_ITEM_NAME + " TEXT,"
            + KEY_ITEM_DESC + " TEXT," + KEY_PRICE + " DECIMAL(10,2)," + KEY_WEIGHT + " REAL);";

    //enumerator for the ordering of a search
    public enum ORDER_ITEM_BY {
        NO_ORDER, ALPHA_A_TO_Z, ALPHA_Z_TO_A, PRICE_LOW_TO_HIGH, PRICE_HIGH_TO_LOW
    }

    public StoreDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        setupTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        deleteDatabase(db);
    }

    public void deleteDatabase(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        setupTables(db);
    }

    private void setupTables(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_ITEMS);
    }

    public long addEntry(ItemModel item) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_ITEM_NAME, item.getName());
        values.put(KEY_ITEM_DESC, item.getDesc());
        values.put(KEY_PRICE, item.getPrice());
        values.put(KEY_WEIGHT, item.getWeight());

        long insert = db.insert(TABLE_ITEMS, null, values);

        return insert;
    }

    public int updateEntry(ItemModel item) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_ITEM_NAME, item.getName());
        values.put(KEY_ITEM_DESC, item.getDesc());
        values.put(KEY_PRICE, item.getPrice());
        values.put(KEY_WEIGHT, item.getWeight());

        return db.update(TABLE_ITEMS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(item.getId()) });
    }

    public void deleteEntry(long id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEMS, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    //Convert database data into item object model
    public ItemModel getItem(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_ITEMS + " WHERE "
                + KEY_ID + " = " + id;
        Log.d(TAG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        ItemModel item = new ItemModel(c.getInt(c.getColumnIndex(KEY_ID)),
                                        c.getString(c.getColumnIndex(KEY_ITEM_NAME)),
                                        c.getString(c.getColumnIndex(KEY_ITEM_DESC)),
                                        c.getDouble(c.getColumnIndex(KEY_PRICE)),
                                        c.getDouble(c.getColumnIndex(KEY_WEIGHT)));

        return item;
    }

    //get all items from the database
    public ArrayList<ItemModel> getAllItemsList() {
        String select_query = "SELECT  * FROM " + TABLE_ITEMS;

        return getItemsFromDatabase(select_query);
    }

    //get items from the database under given conditions
    public ArrayList<ItemModel> getItemsList(
            String name, double min_price, double max_price, ORDER_ITEM_BY order) {

        if(min_price<0){min_price=0;}
        if(max_price<0){max_price=0;}

        String select_query = "SELECT  * FROM " + TABLE_ITEMS
                + " WHERE " + KEY_ITEM_NAME + " LIKE \'%" + name + "%\' AND "
                + KEY_PRICE + " >= " + Double.toString(min_price) + " AND "
                + KEY_PRICE + " <= " + Double.toString(max_price);
        switch (order){
            case ALPHA_A_TO_Z:
                select_query += " ORDER BY " +  KEY_ITEM_NAME + " ASC";
                break;
            case ALPHA_Z_TO_A:
                select_query += " ORDER BY " +  KEY_ITEM_NAME + " DESC";
                break;
            case PRICE_LOW_TO_HIGH:
                select_query += " ORDER BY " +  KEY_PRICE + " ASC";
                break;
            case PRICE_HIGH_TO_LOW:
                select_query += " ORDER BY " +  KEY_PRICE + " DESC";
                break;
        }

        Log.d(TAG, select_query);

        return getItemsFromDatabase(select_query);
    }

    private ArrayList<ItemModel> getItemsFromDatabase(String query){
        ArrayList<ItemModel> item_array_list = new ArrayList<ItemModel>();
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d(TAG, query);

        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                ItemModel item = new ItemModel(c.getInt(c.getColumnIndex(KEY_ID)),
                                            c.getString(c.getColumnIndex(KEY_ITEM_NAME)),
                                            c.getString(c.getColumnIndex(KEY_ITEM_DESC)),
                                            c.getDouble(c.getColumnIndex(KEY_PRICE)),
                                            c.getDouble(c.getColumnIndex(KEY_WEIGHT)));
                item_array_list.add(item);
            } while (c.moveToNext());
        }
        return item_array_list;
    }

    public boolean isEmpty(){
        String select_query = "SELECT COUNT(*) FROM " + TABLE_ITEMS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(select_query, null);
        c.moveToFirst();
        int count= c.getInt(0);
        return count == 0;
    }

    //put in some default values to the database
    public void reinitialize(){
        SQLiteDatabase db = this.getReadableDatabase();
        deleteDatabase(db);
        //Add some random entries into the database
        addEntry(new ItemModel(-1, "Hat","A hat",5.00,0.18));
        addEntry(new ItemModel(-1, "Blue Hat","This hat is blue",6.00,0.19));
        addEntry(new ItemModel(-1, "Red Hat","Not the Linux distro",6.00,0.185));
        addEntry(new ItemModel(-1, "T-Shirt","A T-Shirt",20.00,0.172));
        addEntry(new ItemModel(-1, "GeForce 1050","A weak GPU from nVidea",149.99,0.73));
        addEntry(new ItemModel(-1, "GeForce 1060","A medium GPU from nVidea",284.99,1.1));
        addEntry(new ItemModel(-1, "GeForce 1070","A medium GPU from nVidea",539.99,1.5));
        addEntry(new ItemModel(-1, "GeForce 1080","A powerful GPU from nVidea",674.99,1.5));
        addEntry(new ItemModel(-1, "Radeon RX550","A weak GPU from AMD",150.72,0.662));
        addEntry(new ItemModel(-1, "Radeon RX560","A medium GPU from AMD",164,0.862));
        addEntry(new ItemModel(-1, "Radeon RX570","A medium GPU from AMD",329.99,0.953));
        addEntry(new ItemModel(-1, "Radeon RX580","A powerful GPU from AMD",389.99,1.2));
    }
}