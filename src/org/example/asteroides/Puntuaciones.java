package org.example.asteroides;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class Puntuaciones extends ListActivity {

	private Activity activity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		activity = this;

		setContentView(R.layout.puntuaciones);

		new Thread(new Runnable() {
			public void run() {
				try{
					setListAdapter(
						new MiAdaptador(
							activity,Asteroides.almacen.listaPuntuaciones(10)));
				}catch (Exception e){
				}
			}
		}).start();
	}

	@Override
	protected void onListItemClick(
		ListView listView, View view, int position, long id) {

		super.onListItemClick(listView, view, position, id);

		Object o = getListAdapter().getItem(position);

		Toast.makeText(
			this,
			"Selección: " + Integer.toString(position) + " - " + o.toString(),
			Toast.LENGTH_LONG).show();
	}
}