package org.example.asteroides;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ServicioMusica extends Service {

	@Override
	public void onCreate() {
		Toast.makeText(this, "Servicio creado", Toast.LENGTH_SHORT).show();

		nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

		reproductor = MediaPlayer.create(this, R.raw.mid);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Toast.makeText(
			this, "Servicio arrancado " + startId, Toast.LENGTH_SHORT).show();

		reproductor.start();
	}

	@Override
	public int onStartCommand(Intent intenc, int flags, int idArranque) {
		Toast.makeText(
			this, "Servicio arrancado " + idArranque, Toast.LENGTH_SHORT).show();

		_buildNotification();

		//_notify();

		reproductor.start();

		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "Servicio detenido", Toast.LENGTH_SHORT).show();

		reproductor.stop();

		nm.cancel(ID_NOTIFICACION_CREAR);
	}

	@Override
	public IBinder onBind(Intent intencion) {
		return null;
	}

	@SuppressLint("NewApi")
	private void _buildNotification() {
		Builder builder = new Builder(this);

		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setContentTitle("Creando Servicio de Música");
		builder.setContentText("Reproduciendo música con API actualizado");
		builder.setWhen(System.currentTimeMillis());

		PendingIntent intencionPendiente = PendingIntent.getActivity(
			this, 0, new Intent(this, Asteroides.class), 0);

		builder.setContentIntent(intencionPendiente);

		nm.notify(ID_NOTIFICACION_CREAR, builder.build());
	}

	/**
	 * @deprecated as of HONEYCOMB API ({link _buildNotification)}
	 */
	@SuppressWarnings("deprecation")
	private void _notify() {
		Notification notificacion = new Notification(
			R.drawable.ic_launcher, "Creando Servicio de Música",
			System.currentTimeMillis() );

		PendingIntent intencionPendiente = PendingIntent.getActivity(
			this, 0, new Intent(this, Asteroides.class), 0);

		notificacion.setLatestEventInfo(this, "Reproduciendo música",
			"información adicional", intencionPendiente);

		nm.notify(ID_NOTIFICACION_CREAR, notificacion);
	}

	private NotificationManager nm;
	private static final int ID_NOTIFICACION_CREAR = 1;
	private MediaPlayer reproductor;

}