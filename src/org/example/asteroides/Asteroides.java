package org.example.asteroides;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Asteroides extends BaseActivity {

	public static AlmacenPuntuaciones almacen = new AlmacenPuntuacionesArray();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		bConfigure = (Button) findViewById(R.id.Button02);

		bConfigure.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				lanzarPreferencias(null);
			}
		});

		bAcercaDe = (Button) findViewById(R.id.Button03);

		bAcercaDe.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				lanzarAcercaDe(null);
			}
		});

		bFinish = (Button) findViewById(R.id.Button04);

		bFinish.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				lanzarPuntuaciones(null);
			}
		});

		startService(
			new Intent(Asteroides.this, ServicioMusica.class));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.acercaDe:
				lanzarAcercaDe(null);
				break;
			case R.id.config:
				lanzarPreferencias(null);
				break;
		}

		// true -> consumimos el item, no se propaga

		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		stopService(new Intent(Asteroides.this, ServicioMusica.class));
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onRestoreInstanceState(Bundle bundle){
		super.onRestoreInstanceState(bundle);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle bundle){
		super.onSaveInstanceState(bundle);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	public void lanzarAcercaDe(View view) {
		Intent i = new Intent(this, AcercaDe.class);

		startActivity(i);
	}

	public void lanzarJuego(View view) {
		Intent i = new Intent(this, Juego.class);

		startActivity(i);
	}

	public void lanzarPreferencias(View view) {
		Intent i = new Intent(this, Preferencias.class);

		startActivity(i);
	}

	public void lanzarPuntuaciones(View view) {
		Intent i = new Intent(this, Puntuaciones.class);

		startActivity(i);
	}

	private Button bAcercaDe;
	private Button bConfigure;
	private Button bFinish;

}