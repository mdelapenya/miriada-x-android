package org.example.asteroides.storage;

import java.util.Vector;

public interface AlmacenPuntuaciones {

	public void guardarPuntuacion(int puntos, String nombre, long fecha);

	public Vector<String> listaPuntuaciones(int cantidad);

}