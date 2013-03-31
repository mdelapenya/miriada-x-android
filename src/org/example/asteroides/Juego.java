package org.example.asteroides;

import android.content.Context;
import android.hardware.SensorManager;
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

		_vistaJuego.pausarSensores();

		_vistaJuego.getThread().pausar();
	}

	@Override
	protected void onResume() {
		super.onResume();

		_vistaJuego.reanudarSensores();

		_vistaJuego.getThread().reanudar();
	}

	@Override
	protected void onDestroy() {
		_vistaJuego.getThread().detener();

		_vistaJuego.pausarSensores();

		super.onDestroy();
	}

	protected VistaJuego _vistaJuego;

}