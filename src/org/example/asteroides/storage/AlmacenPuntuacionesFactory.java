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
		else if ("2".equals(type)) {
			return new AlmacenPuntuacionesFicheroExterno(context);
		}
		else if ("3".equals(type)) {
			return new AlmacenPuntuacionesFicheroRecurso(context);
		}
		else if ("4".equals(type)) {
			return new AlmacenPuntuacionesXML_SAX(context);
		}
		else if ("5".equals(type)) {
			return new AlmacenPuntuacionesSQLite(context);
		}

		throw new IllegalArgumentException();
	}

}