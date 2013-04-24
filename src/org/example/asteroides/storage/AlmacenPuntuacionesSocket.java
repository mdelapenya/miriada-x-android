package org.example.asteroides.storage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

import android.util.Log;

public class AlmacenPuntuacionesSocket implements AlmacenPuntuaciones {

	public void guardarPuntuacion(
		final int puntos, final String nombre, long fecha) {

		try {
			new Thread(new Runnable() {
				public void run() {
					try {
						Socket sk = new Socket("192.168.1.14", 1234);

						BufferedReader entrada = new BufferedReader(
							new InputStreamReader(sk.getInputStream()));

						PrintWriter salida = new PrintWriter(
							new OutputStreamWriter(
								sk.getOutputStream()), true);

						salida.println(puntos + " " + nombre);

						String respuesta = entrada.readLine();

						if (!respuesta.equals("OK")) {
							Log.e("Asteroides",
								"Error: respuesta de servidor incorrecta");
						}

						sk.close();
					} catch (Exception e) {
						Log.e("Asteroides", e.toString());
					}
				}
			}).start();

		} catch (Exception e) {
			Log.e("Asteroides", e.toString());
		}
	}

	public Vector<String> listaPuntuaciones(final int cantidad) { 
		Vector<String> result = new Vector<String>();

		try{ 
			Socket sk = new Socket("192.168.1.14", 1234);

			BufferedReader entrada = new BufferedReader(
				new InputStreamReader(sk.getInputStream()));

			PrintWriter salida = new PrintWriter(
				new OutputStreamWriter(sk.getOutputStream()),true);

			salida.println("PUNTUACIONES");

			int n = 0;

			String respuesta;

			do {
				respuesta = entrada.readLine();

				Log.d("Asteroides", "RespuestaServidor: "+respuesta);

				if (respuesta != null) {
					result.add(respuesta);
					n++;
				}
			}
			while (n < cantidad && respuesta != null);

			sk.close();

			return result;
		}catch (Exception e){
			return result;
		}
	}

}