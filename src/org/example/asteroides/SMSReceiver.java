package org.example.asteroides;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class SMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();

		if (action.equals("android.provider.Telephony.SMS_RECEIVED")) {
			Bundle extras = intent.getExtras();

			if (extras != null) {
				Intent intencion = new Intent(context, Asteroides.class);

				intencion.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				context.startActivity(intent);
			}
		}
	}

}