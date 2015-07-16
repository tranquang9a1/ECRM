package com.fu.group10.capstone.apps.teachermobileapp.service;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fu.group10.capstone.apps.teachermobileapp.R;
import com.fu.group10.capstone.apps.teachermobileapp.activity.NotifyActivity;
import com.fu.group10.capstone.apps.teachermobileapp.utils.Constants;
import com.google.android.gms.gcm.GoogleCloudMessaging;


public class RegisterActivity extends Activity {

	Button btnGCMRegister;
	Button btnAppShare;
	GoogleCloudMessaging gcm;
	Context context;
	String regId;

	public static final String REG_ID = "regId";
	private static final String APP_VERSION = "appVersion";

	static final String TAG = "Register Activity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_notify);

		context = getApplicationContext();

		btnGCMRegister = (Button) findViewById(R.id.btnGCMRegister);
		btnGCMRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (TextUtils.isEmpty(regId)) {
					regId = registerGCM();
					Log.d("RegisterActivity", "GCM RegId: " + regId);
				} else {
					Toast.makeText(getApplicationContext(),
							"Already Registered with GCM Server!",
							Toast.LENGTH_LONG).show();
				}
			}
		});

		btnAppShare = (Button) findViewById(R.id.btnAppShare);
		btnAppShare.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (TextUtils.isEmpty(regId)) {
					Toast.makeText(getApplicationContext(), "RegId is empty!",
							Toast.LENGTH_LONG).show();
				} else {
					Intent i = new Intent(getApplicationContext(),
							NotifyActivity.class);
					i.putExtra("regId", regId);
					Log.d("RegisterActivity",
							"onClick of Share: Before starting main activity.");
					startActivity(i);
					finish();
					Log.d("RegisterActivity", "onClick of Share: After finish.");
				}
			}
		});
	}

	public String registerGCM() {

		gcm = GoogleCloudMessaging.getInstance(this);
		regId = getRegistrationId(context);

		if (TextUtils.isEmpty(regId)) {

			registerInBackground();

			Log.d("RegisterActivity",
					"registerGCM - successfully registered with GCM server - regId: "
							+ regId);
		} else {
			Toast.makeText(getApplicationContext(),
					"RegId already available. RegId: " + regId,
					Toast.LENGTH_LONG).show();
		}
		return regId;
	}

	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getSharedPreferences(
				NotifyActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		String registrationId = prefs.getString(REG_ID, "");
		if (registrationId.isEmpty()) {
			Log.i(TAG, "Registration not found.");
			return "";
		}
		int registeredVersion = prefs.getInt(APP_VERSION, Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			Log.i(TAG, "App version changed.");
			return "";
		}
		return registrationId;
	}

	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			Log.d("RegisterActivity",
					"I never expected this! Going down, going down!" + e);
			throw new RuntimeException(e);
		}
	}

	private void registerInBackground() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(context);
					}
					regId = gcm.register(Constants.GOOGLE_PROJECT_ID);
					Log.d("RegisterActivity", "registerInBackground - regId: "
							+ regId);
					msg = "Device registered, registration ID=" + regId;

					storeRegistrationId(context, regId);
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
					Log.d("RegisterActivity", "Error: " + msg);
				}
				Log.d("RegisterActivity", "AsyncTask completed: " + msg);
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
				Toast.makeText(getApplicationContext(),
						"Registered with GCM Server." + msg, Toast.LENGTH_LONG)
						.show();
			}
		}.execute(null, null, null);
	}

	private void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = getSharedPreferences(
				NotifyActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		int appVersion = getAppVersion(context);
		Log.i(TAG, "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(REG_ID, regId);
		editor.putInt(APP_VERSION, appVersion);
		editor.commit();
	}
}
