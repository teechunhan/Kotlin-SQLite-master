package info.camposha.kotlinsqlite

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class DbRecipe(context: Context) :
        SQLiteOpenHelper(context, DATABASE_RECIPE, null, 1) {


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TABLE_RECIPE (ID INTEGER PRIMARY KEY " +
                "AUTOINCREMENT,NAME TEXT,INGREDIENT TEXT,STEP TEXT,TYPE TEXT)")
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE)
        onCreate(db)
    }


    fun insertData(name: String, ingredient: String, step: String, type: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_2, name)
        contentValues.put(COL_3, ingredient)
        contentValues.put(COL_4, step)
        contentValues.put(COL_5, type)
        db.insert(TABLE_RECIPE, null, contentValues)
    }


    fun updateData(id: String, name: String, ingredient: String, step: String, type: String):
            Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_1, id)
        contentValues.put(COL_2, name)
        contentValues.put(COL_3, ingredient)
        contentValues.put(COL_4, step)
        contentValues.put(COL_5, type)
        db.update(TABLE_RECIPE, contentValues, "ID = ?", arrayOf(id))
        return true
    }


    fun deleteData(id : String) : Int {
        val db = this.writableDatabase
        return db.delete(TABLE_RECIPE,"ID = ?", arrayOf(id))
    }


    val allData : Cursor
        get() {
            val db = this.writableDatabase
            val res = db.rawQuery("SELECT * FROM " + TABLE_RECIPE, null)
            return res
        }

    companion object {
        val DATABASE_RECIPE = "stars.db"
        val TABLE_RECIPE = "star_table"
        val COL_1 = "ID"
        val COL_2 = "NAME"
        val COL_3 = "INGREDIENT"
        val COL_4 = "STEP"
        val COL_5 = "TYPE"
    }
}
//end