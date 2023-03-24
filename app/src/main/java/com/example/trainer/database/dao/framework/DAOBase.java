package com.example.trainer.database.dao.framework;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.trainer.database.DatabaseHelper;
import com.example.trainer.database.dao.entityCreators.EntityCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class DAOBase<T> {

    private final DatabaseHelper dbCon;
    private final String table;

    protected EntityCreator<T> entityCreator;

    // Strategy pattern with EntityCreator
    protected DAOBase(EntityCreator<T> entityCreator, String table){
        this.entityCreator = entityCreator;
        this.table = table;
        dbCon = Objects.requireNonNull(DatabaseHelper.getInstance());
    }

    protected SQLiteDatabase readableDB(){
        return dbCon.getReadableDatabase();
    }

    protected SQLiteDatabase writableDB(){
       return dbCon.getWritableDatabase();
    }

    protected T readRow(Cursor cursor){
        return entityCreator.createFrom(cursor  );
    }

    protected List<T> selectFromDb(String clause, String[] args){
        SQLiteDatabase db = readableDB();
        List<T> list = new ArrayList<>();
        System.out.println(clause);
        if(args != null) System.out.println(args[0]);

        Cursor cursor = db.query(table, null, clause, args, null, null, null);
        if(cursor != null){
            while(cursor.moveToNext()){
               T obj = readRow(cursor);
               list.add(obj);
            }
        }
        db.close();
        return list;
    }

    protected void saveToDb(ContentValues cv){
        SQLiteDatabase db = writableDB();
        db.insert(table, null, cv);
        db.close();
    }

    protected String getIdOfLastInsertedRow(){
        SQLiteDatabase db = readableDB();
        Cursor cursor = db.query(table, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToLast();
            db.close();
            String id = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
            cursor.close();
            return id;
        }
        db.close();
        throw new IllegalStateException("Inserted row not found");
    }

    protected void removeFromDb(String clause, String[] args){
        SQLiteDatabase db = writableDB();
        db.delete(table, clause, args);
        db.close();
    }

    protected void updateInDb(ContentValues cv, String clause, String[] args){
        SQLiteDatabase db = writableDB();
        db.update(table, cv, clause, args);
        db.close();
    }


    protected String[] createArgs(Object ...args){
        String[] values = new String[args.length];

        for(int i = 0; i < args.length; i++){
            values[i] = args[i].toString();
        }

        return values;
    }
}
