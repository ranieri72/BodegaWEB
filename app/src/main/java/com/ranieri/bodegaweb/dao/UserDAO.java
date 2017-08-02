package com.ranieri.bodegaweb.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ranieri.bodegaweb.dao.contract.UserContract;
import com.ranieri.bodegaweb.dao.database.BodegaHelper;
import com.ranieri.bodegaweb.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ranie on 1 de jul.
 */

public class UserDAO extends GenericDAO<User> {

    private int indexID;
    private int indexLogin;
    private int indexPassword;
    private int indexAutologin;

    public UserDAO(Context mContext) {
        super(mContext);
        tableName = UserContract.TABLE_NAME;
    }

    public User insert(User user, boolean autoLogin) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        if (autoLogin) {
            setAutoLoginFalse();
            user.setAutoLogin(true);
        }
        ContentValues values = valuesFromObject(user);
        long id = db.insert(UserContract.TABLE_NAME, null, values);
        user.setId(id);

        db.close();
        return user;
    }

    public int update(User user, boolean autoLogin) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] idUser = new String[]{String.valueOf(user.getId())};

        if (autoLogin) {
            setAutoLoginFalse();
            user.setAutoLogin(true);
        }
        ContentValues values = valuesFromObject(user);
        int rowsAffected = db.update(UserContract.TABLE_NAME, values, UserContract.COLUMN_ID + " = ?", idUser);

        db.close();
        return rowsAffected;
    }

    public User selectAutoLogin() {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT * FROM " +
                UserContract.TABLE_NAME +
                " WHERE " +
                UserContract.AUTOLOGIN +
                " = 1;";

        Cursor cursor = db.rawQuery(sql, null);

        User user = null;
        if (cursor.moveToNext()) {
            getColumnIndex(cursor);

            user = new User();
            user.setId(cursor.getLong(indexID));
            user.setLogin(cursor.getString(indexLogin));
            user.setPassword(cursor.getString(indexPassword));
            user.setAutoLogin((cursor.getInt(indexAutologin)) == 1);
        }

        cursor.close();
        db.close();
        return user;
    }

    public int count(User user) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] id = new String[]{String.valueOf(user.getId())};

        String sql = "SELECT COUNT(*) FROM " +
                UserContract.TABLE_NAME +
                " WHERE " +
                UserContract.ID +
                " = ?;";

        Cursor cursor = db.rawQuery(sql, id);

        int count = 0;
        if (cursor.moveToNext()) {
            count = cursor.getInt(0);
        }

        cursor.close();
        db.close();
        return count;
    }

    public List<User> list() {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + UserContract.TABLE_NAME, null);

        List<User> list = valuesFromCursor(cursor);
        cursor.close();
        db.close();
        return list;
    }

    public void setAutoLoginFalse() {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        String sql = "UPDATE " +
                UserContract.TABLE_NAME +
                " SET " +
                UserContract.COLUMN_AUTOLOGIN +
                " = 0;";

        db.execSQL(sql);
        db.close();
    }

    private void getColumnIndex(Cursor cursor) {
        indexID = cursor.getColumnIndex(UserContract.COLUMN_ID);
        indexLogin = cursor.getColumnIndex(UserContract.COLUMN_LOGIN);
        indexPassword = cursor.getColumnIndex(UserContract.COLUMN_PASSWORD);
        indexAutologin = cursor.getColumnIndex(UserContract.COLUMN_AUTOLOGIN);
    }

    private List<User> valuesFromCursor(Cursor cursor) {
        List<User> list = new ArrayList<>();
        User user;

        getColumnIndex(cursor);
        while (cursor.moveToNext()) {
            user = new User();

            user.setId(cursor.getLong(indexID));
            user.setLogin(cursor.getString(indexLogin));
            user.setPassword(cursor.getString(indexPassword));
            user.setAutoLogin((cursor.getInt(indexAutologin)) == 1);

            list.add(user);
        }
        return list;
    }

    @Override
    protected ContentValues valuesFromObject(User user) {
        ContentValues values = new ContentValues();
        values.put(UserContract.COLUMN_ID, user.getId());
        values.put(UserContract.COLUMN_LOGIN, user.getLogin());
        values.put(UserContract.COLUMN_PASSWORD, user.getPassword());
        values.put(UserContract.COLUMN_AUTOLOGIN, (user.isAutoLogin()) ? 1 : 0);
        return values;
    }
}