package org.example.asteroides;

import android.app.ListActivity;
import android.os.Bundle;

public class Puntuaciones extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.puntuaciones);

		setListAdapter(
			new MiAdaptador(this, Asteroides.almacen.listaPuntuaciones(10)));
	}

}