package com.example.louproj;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{


    public static final String DB_NAME = "Database";

    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "M2Users";

    // below variable is for our id column.
    public static final String ID_COL = "id";

    public static final String NAME_COL = "username";

    public static final String Password_COL = "Password";

    public DBHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + Password_COL + " TEXT)";


        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void addNewUser(String Username, String Password) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NAME_COL, Username);
        values.put(Password_COL, Password);


        db.insert(TABLE_NAME, null, values);


        db.close();
    }
    public boolean isValidUser(String user, String pass) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {ID_COL};
        String selection = NAME_COL + " = ?" + " AND " + Password_COL + " = ?";
        String[] selectionArgs = {user, pass};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        boolean isValid = cursor.moveToFirst();
        cursor.close();
        db.close();
        return isValid;
    }
    public boolean isUserExists(String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {ID_COL};
        String selection = NAME_COL + " = ?";
        String[] selectionArgs = {user};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        boolean userExists = cursor.moveToFirst();
        cursor.close();
        db.close();
        return userExists;
    }
    public void deleteUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, NAME_COL + " = ?", new String[]{username});
        db.close();
    }
    public void deleteLastItem() {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_NAME + " WHERE "
                + ID_COL + " = (SELECT MAX(" + ID_COL + ") FROM " + TABLE_NAME + ")";
        db.execSQL(deleteQuery);
        db.close();
    }
    public boolean isValidPassword(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {Password_COL};
        String selection = NAME_COL + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        boolean isValid = false;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int passwordIndex = cursor.getColumnIndex(Password_COL);
                if (passwordIndex != -1) {
                    String storedPassword = cursor.getString(passwordIndex);
                    isValid = password.equals(storedPassword);
                } else {
                    // Handle the case where the password column does not exist in the result set
                    // You may want to log an error or handle it appropriately
                }
            }
            cursor.close();
        }
        db.close();
        return isValid;
    }


    public void updatePassword(String username, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Password_COL, newPassword);
        String selection = NAME_COL + " = ?";
        String[] selectionArgs = {username};
        db.update(TABLE_NAME, values, selection, selectionArgs);
        db.close();
    }


}
