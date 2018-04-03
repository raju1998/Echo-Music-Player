package com.example.manish.echo.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.support.v4.app.INotificationSideChannel
import com.example.manish.echo.Songs

/**
 * Created by manish on 21-Jan-18.
 */
class EchoDatabase : SQLiteOpenHelper {
    var _songList = ArrayList<Songs>()


    object Staticated {
        var DB_version = 1
        val DB_NAME = "FavoriteDatabase"
        val TABLE_NAME = "FavoriteTable"
        val COLUMN_ID = "SongID"
        val COLUMN_SONG_TITLE = "SongTitle"
        val COLUMN_SONG_ARTIST = "SongArtist"
        val COLUMN_SONG_PATH = "SongPath"
    }

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase?.execSQL(
                "CREATE TABLE " + Staticated.TABLE_NAME + "( " + Staticated.COLUMN_ID + " INTEGER," + Staticated.COLUMN_SONG_ARTIST + " STRING," + Staticated.COLUMN_SONG_TITLE + " STRING," + Staticated.COLUMN_SONG_PATH + " STRING );"
        )
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    constructor(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : super(context, name, factory, version)
    constructor(context: Context?) : super(context, Staticated.DB_NAME, null, Staticated.DB_version)

    fun storeAsFavorite(id: Int?, artist: String?, songTitle: String?, path: String?) {
        val db = this.writableDatabase
        var contentValues = ContentValues()
        contentValues.put(Staticated.COLUMN_ID, id)
        contentValues.put(Staticated.COLUMN_SONG_ARTIST, artist)
        contentValues.put(Staticated.COLUMN_SONG_TITLE, songTitle)
        contentValues.put(Staticated.COLUMN_SONG_PATH, path)
        db.insert(Staticated.TABLE_NAME, null, contentValues)
        db.close()
    }

    fun queryDBList(): ArrayList<Songs>? {
        try {
            val db = this.readableDatabase
            val query_params = "SELECT * FROM " + Staticated.TABLE_NAME
            var cSar = db.rawQuery(query_params, null)
            if (cSar.moveToFirst()) {
                do {
                    var _id = cSar.getInt(cSar.getColumnIndexOrThrow(Staticated.COLUMN_ID))
                    var _artist = cSar.getString(cSar.getColumnIndexOrThrow(Staticated.COLUMN_SONG_ARTIST))
                    var _title = cSar.getString(cSar.getColumnIndexOrThrow(Staticated.COLUMN_SONG_TITLE))
                    var _songpath = cSar.getString(cSar.getColumnIndexOrThrow(Staticated.COLUMN_SONG_PATH))
                    _songList.add(Songs(_id.toLong(), _title, _artist, _songpath, 0))

                } while (cSar.moveToNext())
            } else {
                return null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return _songList

    }

    fun checkIfIdExist(_id: Int): Boolean {
        var storeId = -1090
        val db = this.readableDatabase
        val query_params = "SELECT * FROM " + Staticated.TABLE_NAME + " WHERE SongID = '$_id'"
        val cSar = db.rawQuery(query_params, null)
        if (cSar.moveToFirst()) {
            do {
                storeId = cSar.getInt(cSar.getColumnIndexOrThrow(Staticated.COLUMN_ID))
            } while (cSar.moveToNext())
        } else {
            return false
        }
        return storeId != -1090
    }

    fun deleteFavorite(_id: Int) {
        val db = this.writableDatabase
        db.delete(Staticated.TABLE_NAME, Staticated.COLUMN_ID + "=" + _id, null)
        db.close()
    }

    fun checksize(): Int {
        var counter = 0
        val db = this.readableDatabase
        var query_params = "SELECT * FROM " + Staticated.TABLE_NAME
        val cSar = db.rawQuery(query_params, null)
        if (cSar.moveToFirst()) {
            do {
                counter = counter + 1

            } while (cSar.moveToNext())
        } else {
            return 0
        }
        return counter

    }


}