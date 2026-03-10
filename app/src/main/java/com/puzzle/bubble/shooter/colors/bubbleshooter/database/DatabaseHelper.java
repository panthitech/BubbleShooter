package com.puzzle.bubble.shooter.colors.bubbleshooter.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.puzzle.bubble.shooter.colors.bubbleshooter.item.Item;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "star.db";
    public static final int DATABASE_VERSION = 1;
    public static final int INITIAL_COIN = 100;
    public static final String ITEM_COLUMN_NAME = "NAME";
    public static final String ITEM_COLUMN_NUM = "NUMBER";
    public static final String ITEM_TABLE_NAME = "ITEM_TABLE";
    public static final String STAR_COLUMN_LEVEL = "LEVEL";
    public static final String STAR_COLUMN_STAR = "STAR";
    public static final String STAR_TABLE_NAME = "STAR_TABLE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE STAR_TABLE (LEVEL INTEGER PRIMARY KEY AUTOINCREMENT, STAR INTEGER)");
        sqLiteDatabase.execSQL("CREATE TABLE ITEM_TABLE (NAME TEXT PRIMARY KEY, NUMBER INTEGER)");
        initItem(sqLiteDatabase);
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }

    public void initItem(SQLiteDatabase sqLiteDatabase) {
        ContentValues valuesCoin = new ContentValues();
        valuesCoin.put(ITEM_COLUMN_NAME, Item.COIN);
        valuesCoin.put(ITEM_COLUMN_NUM, 100);
        ContentValues valuesColorBall = new ContentValues();
        valuesColorBall.put(ITEM_COLUMN_NAME, Item.COLOR_BALL);
        valuesColorBall.put(ITEM_COLUMN_NUM, 0);
        ContentValues valuesFireball = new ContentValues();
        valuesFireball.put(ITEM_COLUMN_NAME, Item.FIREBALL);
        valuesFireball.put(ITEM_COLUMN_NUM, 0);
        ContentValues valuesBomb = new ContentValues();
        valuesBomb.put(ITEM_COLUMN_NAME, Item.BOMB);
        valuesBomb.put(ITEM_COLUMN_NUM, 0);
        sqLiteDatabase.insert(ITEM_TABLE_NAME, (String) null, valuesCoin);
        sqLiteDatabase.insert(ITEM_TABLE_NAME, (String) null, valuesColorBall);
        sqLiteDatabase.insert(ITEM_TABLE_NAME, (String) null, valuesFireball);
        sqLiteDatabase.insert(ITEM_TABLE_NAME, (String) null, valuesBomb);
    }

    public boolean updateItemNum(String name, int num) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ITEM_COLUMN_NUM, Integer.valueOf(num));
        int id = db.update(ITEM_TABLE_NAME, values, "NAME = ? ", new String[]{name});
        db.close();
        return id != -1;
    }

    public int getItemNum(String name) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(ITEM_TABLE_NAME, new String[]{ITEM_COLUMN_NUM}, "NAME =? ", new String[]{name}, (String) null, (String) null, (String) null, (String) null);
        int number = -1;
        if (cursor.moveToFirst()) {
            number = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return number;
    }

    public boolean insertLevelStar(int star) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STAR_COLUMN_STAR, Integer.valueOf(star));
        long id = db.insert(STAR_TABLE_NAME, (String) null, values);
        db.close();
        if (id == -1) {
            return false;
        }
        return true;
    }

    public boolean updateLevelStar(int levelID, int star) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STAR_COLUMN_STAR, Integer.valueOf(star));
        if (db.update(STAR_TABLE_NAME, values, "LEVEL = ? ", new String[]{String.valueOf(levelID)}) == -1) {
            return false;
        }
        return true;
    }

    public int getLevelStar(int levelID) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(STAR_TABLE_NAME, new String[]{STAR_COLUMN_STAR}, "LEVEL =? ", new String[]{String.valueOf(levelID)}, (String) null, (String) null, (String) null, (String) null);
        int star = -1;
        if (cursor.moveToFirst()) {
            star = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return star;
    }

    public ArrayList<Integer> getAllLevelStar() {
        ArrayList<Integer> stars = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM STAR_TABLE", (String[]) null);
        if (cursor.moveToFirst()) {
            do {
                stars.add(Integer.valueOf(cursor.getInt(1)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return stars;
    }
}
