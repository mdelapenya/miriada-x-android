package org.example.asteroides.storage;

import android.content.Context;

public class AlmacenPuntuacionesFactory {

	public static AlmacenPuntuaciones getAlmacenPuntuaciones(
		String type, Context context) {

		if ("0".equals(type)) {
			return new AlmacenPuntuacionesPreferencias(context);
		}
		else if ("1".equals(type)) {
			return new AlmacenPuntuacionesFicheroInterno(context);
		}

		throw new IllegalArgumentException();
	}

}