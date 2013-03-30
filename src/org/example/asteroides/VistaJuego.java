package org.example.asteroides;

import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class VistaJuego extends View {

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

	public VistaJuego(Context context, AttributeSet attrs) {
		super(context, attrs);

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

	protected void actualizaFisica() {
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
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		for (Grafico asteroide : _asteroides) {
			asteroide.dibujaGrafico(canvas);
		}

		nave.dibujaGrafico(canvas);

		ultimoProceso = System.currentTimeMillis();
		thread.start();
	}

	private class ThreadJuego extends Thread {
		@Override
		public void run() {
			while (true) {
				actualizaFisica();
			}
		}
	}

}