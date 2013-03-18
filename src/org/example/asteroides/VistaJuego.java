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

	public VistaJuego(Context context, AttributeSet attrs) {
		super(context, attrs);

		Drawable drawableNave, drawableAsteroide, drawableMisil;

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
	}

	@Override
	protected void onSizeChanged(
		int ancho, int alto, int ancho_inter, int alto_inter) {

		super.onSizeChanged(ancho, alto, ancho_inter, alto_inter);

		// Una vez que conocemos nuestro ancho y alto.

		for (Grafico asteroide : _asteroides) {
			asteroide.setPosX(Math.random() * (ancho - asteroide.getAncho()));
			asteroide.setPosY(Math.random() * (alto - asteroide.getAlto()));
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		for (Grafico asteroide : _asteroides) {
			asteroide.dibujaGrafico(canvas);
		}
	}

}