package org.example.asteroides.storage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class AlmacenPuntuacionesFicheroExterno implements AlmacenPuntuaciones {

	private static String FICHERO;
	private Context context;

	public AlmacenPuntuacionesFicheroExterno(Context context) {
		FICHERO = Environment.getExternalStorageDirectory() +
			"/puntuaciones.txt";

		this.context = context;
	}

	public void guardarPuntuacion(int puntos, String nombre, long fecha) {
		String stadoSD = Environment.getExternalStorageState();

		if (!stadoSD.equals(Environment.MEDIA_MOUNTED)) {
			Toast.makeText(
				context, "No puedo escribir en la memoria externa",
					Toast.LENGTH_LONG).show();

			return;
		}

		try {
			FileOutputStream f = new FileOutputStream(FICHERO, true);

			String texto = puntos + " " + nombre + "\n";

			f.write(texto.getBytes());
			f.close();
		} catch (Exception e) {
			Log.e("Asteroides", e.getMessage(), e);
		}
	}

	public Vector<String> listaPuntuaciones(int cantidad) {
		Vector<String> result = new Vector<String>();

		String stadoSD = Environment.getExternalStorageState();

		if (!stadoSD.equals(Environment.MEDIA_MOUNTED) && 
			!stadoSD.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {

			Toast.makeText(
				context, "No puedo leer en la memoria externa",
					Toast.LENGTH_LONG).show();

			return result;
		}

		try {
			FileInputStream f = new FileInputStream(FICHERO);

			BufferedReader entrada = new BufferedReader(
				new InputStreamReader(f));

			int n = 0;

			String linea;

			do {
				linea = entrada.readLine();

				if (linea != null) {
					result.add(linea);
					n++;
				}
			} while (n < cantidad && linea != null);

			f.close();
		} catch (Exception e) {
			Log.e("Asteroides", e.getMessage(), e);
		}

		return result;
	}

}