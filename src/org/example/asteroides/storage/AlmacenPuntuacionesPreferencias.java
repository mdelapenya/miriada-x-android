package org.example.asteroides.storage;

import java.util.Vector;

import android.content.Context;
import android.content.SharedPreferences;

public class AlmacenPuntuacionesPreferencias implements AlmacenPuntuaciones {

	private static String PREFERENCIAS = "puntuaciones";
	private Context context;

	public AlmacenPuntuacionesPreferencias(Context context) {
		this.context = context;
	}

	public void guardarPuntuacion(int puntos, String nombre, long fecha) {
		SharedPreferences preferencias = context.getSharedPreferences(
			PREFERENCIAS, Context.MODE_PRIVATE);

		SharedPreferences.Editor editor = preferencias.edit();

		for (int n = 9; n >= 1; n--) {
			editor.putString(
				"puntuacion-" + n,
				preferencias.getString("puntuacion-" + (n - 1), ""));
		}

		editor.putString("puntuacion-0", puntos + " " + nombre);

		editor.commit();
	}

	public Vector<String> listaPuntuaciones(int cantidad) {
		Vector<String> result = new Vector<String>();

		SharedPreferences preferencias = context.getSharedPreferences(
			PREFERENCIAS, Context.MODE_PRIVATE);

		for (int n = 0; n <= 9; n++) {
			String puntuacion = preferencias.getString("puntuacion-" + n, "");

			if (puntuacion != "") {
				result.add(puntuacion);
			}
		}

		return result;
	}

}