package org.example.asteroides;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Notification.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Asteroides extends BaseActivity {

	public static AlmacenPuntuaciones almacen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SharedPreferences sharedPreferences =
			PreferenceManager.getDefaultSharedPreferences(this);

		String prefStorage = sharedPreferences.getString("almacenamiento", "1");

		if ("0".equals(prefStorage)) {
			almacen = new AlmacenPuntuacionesPreferencias(this);
		}
		else if ("1".equals(prefStorage)) {
			almacen = new AlmacenPuntuacionesFicheroInterno(this);
		}

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

		bSocorro = (Button) findViewById(R.id.Button05);

		nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

		bSocorro.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				_buildNotification();
			}
		});

		startService(
			new Intent(Asteroides.this, ServicioMusica.class));
	}

	@Override 
	protected void onActivityResult (
		int requestCode, int resultCode, Intent data){

		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 1234 && resultCode == RESULT_OK && data != null) {
			int puntuacion = data.getExtras().getInt("puntuacion");

			String nombre = "Yo";

			// Mejor leerlo desde un Dialog o una nueva actividad
			//AlertDialog.Builder

			almacen.guardarPuntuacion(
				puntuacion, nombre, System.currentTimeMillis());

			lanzarPuntuaciones(null);
		}
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

		nm.cancel(ID_NOTIFICACION_CREAR);

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

		startActivityForResult(i, 1234);
	}

	public void lanzarPreferencias(View view) {
		Intent i = new Intent(this, Preferencias.class);

		startActivity(i);
	}

	public void lanzarPuntuaciones(View view) {
		Intent i = new Intent(this, Puntuaciones.class);

		startActivity(i);
	}

	@SuppressLint("NewApi")
	private void _buildNotification() {
		Builder builder = new Builder(this);

		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setContentTitle("Socorro!!");
		builder.setContentText("¿Necesitas ayuda?");

		long when = System.currentTimeMillis() + (5 * 10000);

		builder.setWhen(when);

		builder.setVibrate(morseVibratePattern);

		builder.setSound(
			Uri.parse(
				"android.resource://org.example.asteroides/" + R.raw.socorro));

		PendingIntent intencionPendiente = PendingIntent.getActivity(
			this, 0, new Intent(this, this.getClass()), 0);

		builder.setContentIntent(intencionPendiente);

		nm.notify(ID_NOTIFICACION_CREAR, builder.build());
	}

	private Button bAcercaDe;
	private Button bConfigure;
	private Button bFinish;
	private Button bSocorro;

	private static final long[] morseVibratePattern =
		new long[] {0, 100, 30, 100, 30, 100, 30, 300, 30, 300, 30, 300,
			30, 100, 30, 100, 30, 100};

	private NotificationManager nm;
	private static final int ID_NOTIFICACION_CREAR = 1;

}