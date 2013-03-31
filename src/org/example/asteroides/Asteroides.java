package org.example.asteroides;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Asteroides extends Activity {

	public static AlmacenPuntuaciones almacen = new AlmacenPuntuacionesArray();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		_showToast("onCreate");

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

		_showToast("onDestroy");
	}

	@Override
	protected void onPause() {
		super.onPause();

		_showToast("onPause");
	}

	@Override
	protected void onRestart() {
		super.onRestart();

		_showToast("onRestart");
	}

	@Override
	protected void onResume() {
		super.onResume();

		_showToast("onResume");
	}

	@Override
	protected void onStart() {
		super.onStart();

		_showToast("onStart");
	}

	@Override
	protected void onStop() {
		super.onStop();

		_showToast("onStop");
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

	private void _showToast(String method) {
		Toast.makeText(this, method, Toast.LENGTH_SHORT).show();
	}

	private Button bAcercaDe;
	private Button bConfigure;
	private Button bFinish;

}