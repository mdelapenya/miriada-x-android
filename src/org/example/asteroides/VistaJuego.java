package org.example.asteroides;

import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class VistaJuego extends View implements SensorEventListener {

	// //// ASTEROIDES //////

	private Vector<Grafico> _asteroides; // Vector con los Asteroides
	private int numAsteroides = 5; // Número inicial de asteroides
	private int numFragmentos = 3; // Fragmentos en que se divide

	// NAVE //
	private Grafico nave;// Gráfico de la nave
	private int giroNave; // Incremento de dirección
	private float aceleracionNave; // aumento de velocidad

	// Incremento estándar de giro y aceleración

	private static final int PASO_GIRO_NAVE = 5;
	private static final float PASO_ACELERACION_NAVE = 0.5f;

	// //// THREAD Y TIEMPO //////

	// Thread encargado de procesar el juego
	private ThreadJuego thread = new ThreadJuego();

	// Cada cuanto queremos procesar cambios (ms)
	private static int PERIODO_PROCESO = 50;

	// Cuando se realizó el último proceso
	private long ultimoProceso = 0;

	// Misil //
	private float lastEventX = 0;
	private float lastEventY = 0;
	private boolean disparo = false;

	public VistaJuego(Context context, AttributeSet attrs) {
		super(context, attrs);

		SensorManager mSensorManager =
			(SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

		List<Sensor> listSensors = mSensorManager.getSensorList(
			Sensor.TYPE_ORIENTATION);

		if (!listSensors.isEmpty()) {
			Sensor orientationSensor = listSensors.get(0);
			mSensorManager.registerListener(
				this, orientationSensor, SensorManager.SENSOR_DELAY_GAME);
		}

		Drawable drawableNave, drawableAsteroide, drawableMisil;

		drawableNave =
			context.getResources().getDrawable(R.drawable.nave);

		drawableAsteroide =
			context.getResources().getDrawable(R.drawable.asteroide1);

		_asteroides = new Vector<Grafico>();

		for (int i = 0; i < numAsteroides; i++) {
			Grafico asteroide = new Grafico(this, drawableAsteroide);

			asteroide.setIncY(Math.random() * 4 - 2);
			asteroide.setIncX(Math.random() * 4 - 2);
			asteroide.setAngulo((int) (Math.random() * 360));
			asteroide.setRotacion((int) (Math.random() * 8 - 4));

			_asteroides.add(asteroide);
		}

		nave = new Grafico(this, drawableNave);
	}

	protected synchronized void actualizaFisica() {
		long ahora = System.currentTimeMillis();

		// No hagas nada si el período de proceso no se ha cumplido.

		if (ultimoProceso + PERIODO_PROCESO > ahora) {
			return;
		}

		// Para una ejecución en tiempo real calculamos retardo

		double retardo = (ahora - ultimoProceso) / PERIODO_PROCESO;

		ultimoProceso = ahora; // Para la próxima vez

		// Actualizamos velocidad y dirección de la nave a partir de 
		// giroNave y aceleracionNave (según la entrada del jugador)

		nave.setAngulo((int) (nave.getAngulo() + giroNave * retardo));

		double nIncX = nave.getIncX() + aceleracionNave *
			Math.cos(Math.toRadians(nave.getAngulo())) * retardo;

		double nIncY = nave.getIncY() + aceleracionNave *
			Math.sin(Math.toRadians(nave.getAngulo())) * retardo;

		// Actualizamos si el módulo de la velocidad no excede el máximo

		if (Math.hypot(nIncX,nIncY) <= Grafico.getMaxVelocidad()){
			nave.setIncX(nIncX);
			nave.setIncY(nIncY);
		}

		// Actualizamos posiciones X e Y

		nave.incrementaPos(retardo);

		for (Grafico asteroide : _asteroides) {
			asteroide.incrementaPos(retardo);
		}
	}

	@Override
	protected void onSizeChanged(
		int ancho, int alto, int ancho_inter, int alto_inter) {

		super.onSizeChanged(ancho, alto, ancho_inter, alto_inter);

		// Una vez que conocemos nuestro ancho y alto.

		for (Grafico asteroide : _asteroides) {
			do{
				asteroide.setPosX(Math.random()*(ancho-asteroide.getAncho()));
				asteroide.setPosY(Math.random()*(alto-asteroide.getAlto()));
			}
			while(asteroide.distancia(nave) < (ancho+alto)/5);
		}

		int x_centro = (super.getWidth() - nave.getAncho()) / 2;
		int y_centro = (super.getHeight() - nave.getAlto()) / 2;

		nave.setPosX(x_centro);
		nave.setPosY(y_centro);

		ultimoProceso = System.currentTimeMillis();
		thread.start();
	}

	@Override
	public boolean onTouchEvent (MotionEvent event) {
		super.onTouchEvent(event);

		float currentEventX = event.getX();
		float currentEventY = event.getY();

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				disparo = true;

				break;
			case MotionEvent.ACTION_MOVE:
				float desplazamientoX = Math.abs(currentEventX - lastEventX);
				float desplazamientoY = Math.abs(currentEventY - lastEventY);

				if (desplazamientoY < 6 && desplazamientoX > 6){
					// desplazamiento horizontal : x > y

					giroNave = Math.round((currentEventX - lastEventX) / 2);
					disparo = false;
				}
				else if (desplazamientoX < 6 && desplazamientoY > 6){
					// desplazamiento vertical: y > x

					// aceleramos si la Y actual es menor que la anterior

					if (lastEventY > currentEventY) {
						aceleracionNave =
							Math.round((lastEventY - currentEventY) / 100);
					}
					else {
						aceleracionNave = 0;
					}

					disparo = false;
				}

				break;
			case MotionEvent.ACTION_UP:
				giroNave = 0;
				aceleracionNave = 0;

				if (disparo){
					//ActivaMisil();
				}

				break;
		}

		lastEventX = currentEventX;
		lastEventY = currentEventY;

		return true;
	}

	@Override
	protected synchronized void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		for (Grafico asteroide : _asteroides) {
			asteroide.dibujaGrafico(canvas);
		}

		nave.dibujaGrafico(canvas);
	}

	private class ThreadJuego extends Thread {
		@Override
		public void run() {
			while (true) {
				actualizaFisica();
			}
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}

	private boolean hayValorInicial = false;
	private float valorInicial;

	@Override 
	public void onSensorChanged(SensorEvent event) {
		float valor = event.values[1];

		if (!hayValorInicial){
			valorInicial = valor;
			hayValorInicial = true;
		}

		giroNave = (int) (valor - valorInicial) / 3 ;
	}

}