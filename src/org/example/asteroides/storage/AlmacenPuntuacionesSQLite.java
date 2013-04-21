package org.example.asteroides.storage;

import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlmacenPuntuacionesSQLite extends SQLiteOpenHelper
	implements AlmacenPuntuaciones {

	// Métodos de SQLiteOpenHelper
	public AlmacenPuntuacionesSQLite(Context context) {
		super(context, "puntuaciones", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(
			"CREATE TABLE puntuaciones (" +
				"_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"puntos INTEGER, nombre TEXT, fecha LONG)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// En caso de una nueva versión habría que actualizar las tablas
	}

	// Métodos de AlmacenPuntuaciones
	public void guardarPuntuacion(int puntos, String nombre, long fecha) {
		SQLiteDatabase db = getWritableDatabase();

		String sql = "INSERT INTO puntuaciones VALUES (?,?,?,?)";
		Object[] bindArgs = new Object[] {null, puntos, nombre, fecha};

		db.execSQL(sql, bindArgs);
	}

	public Vector<String> listaPuntuaciones(int cantidad) {
		Vector<String> result = new Vector<String>();

		SQLiteDatabase db = getReadableDatabase();

		String[] campos = {"puntos", "nombre"};
		Cursor cursor = db.query(
			"puntuaciones", campos, null, null, null, null, "puntos DESC",
			Integer.toString(cantidad));

		while (cursor.moveToNext()) {
			result.add(cursor.getInt(0) + " " + cursor.getString(1));
		}

		cursor.close();

		return result;
	}

}