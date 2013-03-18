package org.example.asteroides;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Juego extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.juego);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.juego, menu);

		return true;
	}

}