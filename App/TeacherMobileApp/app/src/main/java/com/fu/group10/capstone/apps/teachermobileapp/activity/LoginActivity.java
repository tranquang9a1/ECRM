package com.fu.group10.capstone.apps.teachermobileapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;


import com.fu.group10.capstone.apps.teachermobileapp.R;
import com.fu.group10.capstone.apps.teachermobileapp.dao.UserDAO;
import com.fu.group10.capstone.apps.teachermobileapp.model.EquipmentCategory;
import com.fu.group10.capstone.apps.teachermobileapp.model.User;
import com.fu.group10.capstone.apps.teachermobileapp.service.ShareExternalServer;
import com.fu.group10.capstone.apps.teachermobileapp.service.SynchronizeService;
import com.fu.group10.capstone.apps.teachermobileapp.utils.Constants;
import com.fu.group10.capstone.apps.teachermobileapp.utils.DatabaseHelper;
import com.fu.group10.capstone.apps.teachermobileapp.utils.DialogUtils;
import com.fu.group10.capstone.apps.teachermobileapp.utils.ParseUtils;
import com.fu.group10.capstone.apps.teachermobileapp.utils.RequestSender;
import com.fu.group10.capstone.apps.teachermobileapp.utils.Utils;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by QuangTV on 5/30/2015.
 */
public class LoginActivity  extends ActionBarActivity {

    private static final String result ="";
    private static final String defaultPassword = "123ecrm";
    private EditText usernameTextView;
    private EditText passwordTextView;
    private String name;
    Context context;
    ProgressDialog progress;

    private static User user;

    GoogleCloudMessaging gcm;

    public static final String REG_ID = "regId";
    private static final String APP_VERSION = "appVersion";

    static final String TAG = "Notify Activity";
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    private boolean isConnect = false;


    ShareExternalServer appUtil;
    String regId;
    AsyncTask<Void, Void, String> shareRegidTask;
    AsyncTask<String, Void, byte[]> BackgroundTask;

    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        isConnect = Utils.isOnline();
        SharedPreferences sp = getSharedPreferences("LoginState", MODE_PRIVATE);
        boolean stateLogin = sp.getBoolean("LoginState", false);
        String username = sp.getString("username", null);
        if (stateLogin && username != null) {
            if (isConnect) {
                syncData(username);
            }
            openMainActivity(username);
        } else {

            context = getApplicationContext();

            usernameTextView = (EditText) findViewById(R.id.username);

            passwordTextView = (EditText) findViewById(R.id.password);
            passwordTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                    if (id == EditorInfo.IME_NULL) {
                        initLogin();
                        return true;
                    }
                    return false;
                }
            });

            Button loginButton = (Button) findViewById(R.id.sign_in_button);
            loginButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    //sendSMS();
                    initLogin();
                }
            });
        }




    }


    /**
     * Validate Login form and authenticate.
     */
    public void initLogin() {
        hideSoftKeyboard(this);

        usernameTextView.setError(null);
        passwordTextView.setError(null);

        String username = usernameTextView.getText().toString();
        String password = passwordTextView.getText().toString();

        boolean cancelLogin = false;
        View focusView = null;

        if (TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordTextView.setError(getString(R.string.invalid_password));
            focusView = passwordTextView;
            cancelLogin = true;
        }

        if (TextUtils.isEmpty(username) && !isUsernameValid(username)) {
            usernameTextView.setError(getString(R.string.field_required));
            focusView = usernameTextView;
            cancelLogin = true;
        }

        if (cancelLogin) {
            // error in login
            focusView.requestFocus();
        } else {
            // show progress spinner, and start background task to login
            //Map<String, String> params = new HashMap<String, String>();
            //params.put("username", username);
            //params.put("password", password);
            progress = ProgressDialog.show(this, "", "Đang đăng nhập", true);
            if (isConnect) {
                String url = Constants.API_LOGIN + "?username="+username+"&password="+password;
                RequestSender sender = new RequestSender();
                sender.start(url, new RequestSender.IRequestSenderComplete() {
                    @Override
                    public void onRequestComplete(String result) {
                        user = ParseUtils.parseUserJson(result);
                        progress.dismiss();
                        if (user != null ) {


                            if (user.getRole().equalsIgnoreCase("Teacher") && user.getLastLogin() == null ) {
                                firstLogin(user.getPassword());
                            } else {
                                if (TextUtils.isEmpty(regId)) {
                                    regId = registerGCM();
                                    Log.d("RegisterActivity", "GCM RegId: " + regId);
                                } else {
                                    Log.d("RegisterActivity", "Already Registered with GCM Server!");
                                }
                                showProgressSync();
                                registerWithServer(user.getUsername());
                                SharedPreferences sp = getSharedPreferences("LoginState", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putBoolean("LoginState", true);
                                editor.putString("username", user.getUsername());
                                editor.commit();
                                importUser();
                                syncData(user.getUsername());
                                openMainActivity(user.getUsername());


                            }
                        } else {
                            DialogUtils.showAlert(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu không đúng");
                        }

                    }

                });
            } else {
                loginNoInternet(username, password);
                if (user != null) {
                    SharedPreferences sp = getSharedPreferences("LoginState", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("LoginState", true);
                    editor.putString("username", user.getUsername());
                    editor.commit();
                    openMainActivity(user.getUsername());
                } else {
                    DialogUtils.showAlert(this, "Tên đăng nhập hoặc mật khẩu không đúng");
                }
                progress.dismiss();
            }







        }
    }

    private void showProgressSync() {
        progress = ProgressDialog.show(this, "", "Đang đồng bộ thông tin với server", true);
    }
    private boolean isPasswordValid(String password) {
        //add your own logic
        return password.length() > 4 || password.length() < 20;
    }
    private boolean isUsernameValid(String username) {
        return username.length() > 6 || username.length() < 20;
    }


    void openMainActivity(String username) {



        Intent intent = new Intent(this, ListRoomActivity.class);
        //intent.putParcelableArrayListExtra("listClass", listClassroom);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }

    public void firstLogin(String password) {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        intent.putExtra("current_password", password);
        startActivity(intent);
        finish();
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
           Log.d("RegisterActivity", "RegId already available. RegId: " + regId);
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
        } catch (PackageManager.NameNotFoundException e) {
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
                Log.d("RegisterActivity","Registered with GCM Server." + msg);
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

    public void registerWithServer(final String username) {
        appUtil = new ShareExternalServer();


        Log.d("MainActivity", "regId: " + regId);

        final Context context = this;
        shareRegidTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String result = appUtil.shareRegIdWithAppServer(context, regId, username);
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                shareRegidTask = null;
                Log.d("RegisterActivity", result);
            }


        };
        shareRegidTask.execute(null, null, null);


    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void importUser() {

        db = new DatabaseHelper(getApplicationContext());
        db.deleteUser();
        UserDAO dao = new UserDAO();
        dao.setUsername(user.getUsername());
        dao.setDeviceId(regId);
        dao.setFullname(user.getFullname());
        dao.setPassword(user.getPassword());
        dao.setPhone(user.getPhone());
        db.insertUser(dao);
        progress.dismiss();
    }

    public void loginNoInternet(String username, String password) {
        db = new DatabaseHelper(getApplicationContext());
        user = db.login(username, password);

    }


    public void syncData(String username) {
        syncCategory(username);
        startService(new Intent(this, SynchronizeService.class));
    }

    public void syncCategory(String username) {
        RequestSender sender = new RequestSender();
        sender.start(Constants.API_GET_CATEGORY + username, new RequestSender.IRequestSenderComplete() {
            @Override
            public void onRequestComplete(String result) {
                List<EquipmentCategory> equipments = ParseUtils.parseEquipmentCategory(result);
                Log.d("Sync category", equipments.size() + "");
                new ImageDownloader().execute(equipments);
            }
        });
    }



    private byte[] getLogoImage(String url) {
        try {
            URL imageUrl = new URL(url);
            URLConnection ucon = imageUrl.openConnection();
            InputStream is = ucon.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            ByteArrayBuffer baf = new ByteArrayBuffer(500);
            int current = 0;

            while ((current = bis.read()) != -1) {
                baf.append((byte) current);

            }
            return baf.toByteArray();


        } catch (Exception e) {
            Log.d("ImageManager", "Error: " + e.toString());
            return null;
        }

    }
    public void insertCategory(String id, String name, byte[] image) {
        db = new DatabaseHelper(this);
        db.insertEquipment(id, name, image);
    }

    private class ImageDownloader extends AsyncTask<List<EquipmentCategory>, Void, Void> {

        private ProgressDialog progressDialog;

        @Override
        protected Void doInBackground(List<EquipmentCategory>... lists) {
            for (int i =0; i < lists[0].size(); i++) {
                EquipmentCategory ec = lists[0].get(i);
                insertCategory(ec.getId(), ec.getName(),getLogoImage(Constants.RESOURCE_URL + ec.getImageUrl()));
            }
            return null;
        }

        @Override
        protected void onPreExecute() {



        }

        @Override
        protected void onPostExecute(Void result) {


        }

    }





}
