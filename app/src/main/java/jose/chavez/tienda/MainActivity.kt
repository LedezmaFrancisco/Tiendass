package jose.chavez.tienda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TiendaDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "tiendas.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_NAME = "tiendas"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_DIRECCION = "direccion"
        private const val COLUMN_TELEFONO = "telefono"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NOMBRE TEXT, $COLUMN_DIRECCION TEXT, $COLUMN_TELEFONO TEXT)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertTienda(tienda: Tienda) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NOMBRE, tienda.nombre)
        values.put(COLUMN_DIRECCION, tienda.direccion)
        values.put(COLUMN_TELEFONO, tienda.telefono)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun updateTienda(tienda: Tienda) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NOMBRE, tienda.nombre)
        values.put(COLUMN_DIRECCION, tienda.direccion)
        values.put(COLUMN_TELEFONO, tienda.telefono)
        db.update(TABLE_NAME, values, "$COLUMN_ID=?", arrayOf(tienda.id.toString()))
        db.close()
    }

    fun deleteTienda(tienda: Tienda) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(tienda.id.toString()))
        db.close()
    }

    fun getAllTiendas(): List<Tienda> {
        val tiendas = mutableListOf<Tienda>()
        val db = this.readableDatabase
        val selectAllQuery = "SELECT * FROM $TABLE_NAME"
        val cursor: Cursor? = db.rawQuery(selectAllQuery, null)
        if (cursor?.moveToFirst() == true) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE))
                val direccion = cursor.getString(cursor.getColumnIndex(COLUMN_DIRECCION))
                val telefono = cursor.getString(cursor.getColumnIndex(COLUMN_TELEFONO))
                tiendas.add(Tienda(id, nombre, direccion, telefono))
            } while (cursor.moveToNext())
        }
        cursor?.close()
        db.close()
        return tiendas
    }
}

class MainActivity : AppCompatActivity() {
    private lateinit var tiendaAdapter: TiendaAdapter
    private lateinit var tiendaList: MutableList<Tienda>
    private lateinit var tiendaDatabaseHelper:
}