package org.example.asteroides;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		_showToast("onCreate");
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

	private void _showToast(String method) {
		Toast.makeText(
			this, method + " " + this.getTitle(), Toast.LENGTH_SHORT).show();
	}

}
