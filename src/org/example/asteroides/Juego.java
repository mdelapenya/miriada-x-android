package org.example.asteroides;

import android.os.Bundle;
import android.view.Menu;

public class Juego extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.juego);

		_vistaJuego = (VistaJuego) findViewById(R.id.VistaJuego);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.juego, menu);

		return true;
	}

	@Override
	protected void onPause() {
		super.onPause();

		_vistaJuego.getThread().pausar();
	}

	@Override
	protected void onResume() {
		super.onResume();

		_vistaJuego.getThread().reanudar();
	}

	@Override
	protected void onDestroy() {
		_vistaJuego.getThread().detener();

		super.onDestroy();
	}

	protected VistaJuego _vistaJuego;

}