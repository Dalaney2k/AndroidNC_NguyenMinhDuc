package deso1.nguyenminhduc.dlu_21a100100097;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "food.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_FOOD = "foods";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "tenfood";
    private static final String COLUMN_PRICE = "gia";
    private static final String COLUMN_IMAGE = "imageUrl";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_FOOD = "CREATE TABLE IF NOT EXISTS foods ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "tenfood TEXT, "
                + "gia REAL, "
                + "imageUrl TEXT)";
        db.execSQL(CREATE_TABLE_FOOD);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS foods");
        onCreate(db);
    }


    public void addFood(food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, food.getTenfood());
        values.put(COLUMN_PRICE, food.getGia());
        values.put("imageUrl", food.getImageUrl());
        db.insert(TABLE_FOOD, null, values);
        db.close();
    }

    public void deleteFood(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FOOD, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<food> getAllFood() {
        List<food> foodList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM foods", null);
        if (cursor.moveToFirst()) {
            do {
                food f = new food(
                        String.valueOf(cursor.getInt(0)),
                        cursor.getString(1),
                        (int) cursor.getDouble(2),
                        cursor.getString(3)
                );
                foodList.add(f);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return foodList;
    }

}

