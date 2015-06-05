package com.fu.group10.apps.teacher.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import com.fu.group10.apps.teacher.model.User;
import com.fu.group10.apps.teacher.R;
import com.fu.group10.apps.teacher.utils.ParseUtils;


/**
 * Created by QuangTV on 5/30/2015.
 */
public class LoginActivity extends Activity{

    private static final String result =  "{'username':'quangtv','phone': '1123123', 'classId':2, 'role': true}";
    private static final String defaultPassword = "123ecrm";
    private AutoCompleteTextView usernameTextView;
    private EditText passwordTextView;

    private static User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameTextView = (AutoCompleteTextView) findViewById(R.id.username);

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

                initLogin();
            }
        });



    }


    /**
     * Validate Login form and authenticate.
     */
    public void initLogin() {


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
//            Map<String, String> params = new HashMap<String, String>();
//            params.put("username", username);
//            params.put("password", password);
//            RequestSender sender = new RequestSender();
//            String url = Constants.API_LOGIN;
//            Log.d("API Call", "Send API" + url );
//
//            sender.method = "POST";
//            sender.param = params;
//
//            sender.start(url, new RequestSender.IRequestSenderComplete() {
//                @Override
//                public void onRequestComplete(String result) {
//                    result = result == null ? "" : result;
//
//
//                }
//            });


            user = ParseUtils.parseUserJson(result);
            if (user.role && password.equals(defaultPassword)) {
                firstLogin(defaultPassword);
            } else if (user.role && !password.equals(defaultPassword)) {
                openMainActivity();
            } else {
                Toast.makeText(LoginActivity.this, "Username or password are incorrect!", Toast.LENGTH_LONG);

            }


        }
    }

    private boolean isPasswordValid(String password) {
        //add your own logic
        return password.length() > 4 || password.length() < 20;
    }
    private boolean isUsernameValid(String username) {
        return username.length() > 6 || username.length() < 20;
    }




    void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("id", 123);
        startActivity(intent);
        finish();
    }

    public void firstLogin(String password) {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        intent.putExtra("current_password", password);
        startActivity(intent);
        finish();
    }




}