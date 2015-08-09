package com.fu.group10.capstone.apps.teachermobileapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fu.group10.capstone.apps.teachermobileapp.R;
import com.fu.group10.capstone.apps.teachermobileapp.model.Result;
import com.fu.group10.capstone.apps.teachermobileapp.utils.Constants;
import com.fu.group10.capstone.apps.teachermobileapp.utils.DialogUtils;
import com.fu.group10.capstone.apps.teachermobileapp.utils.ParseUtils;
import com.fu.group10.capstone.apps.teachermobileapp.utils.RequestSender;


/**
 * Created by QuangTV on 5/31/2015.
 */
public class ChangePasswordActivity extends Activity {

    private EditText currentPasswordTextView;
    private EditText newPasswordTextView;
    private EditText confirmPasswordTextView;
    TextView txtStatus;
    String username;

    SharedPreferences sp;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        sp = getSharedPreferences("LoginState", MODE_PRIVATE);
        boolean stateLogin = sp.getBoolean("LoginState", false);
        username = sp.getString("username", null);
        password =  sp.getString("password", null);
        currentPasswordTextView = (EditText) findViewById(R.id.current_password);
        newPasswordTextView = (EditText) findViewById(R.id.new_password);
        confirmPasswordTextView = (EditText) findViewById(R.id.confirm_password);

        Button btnUpdate = (Button) findViewById(R.id.change_password_button);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

    }


    public void changePassword() {

        String currentPassword = currentPasswordTextView.getText().toString();
        final String newPassword = newPasswordTextView.getText().toString();
        String confirmPassword = confirmPasswordTextView.getText().toString();

        boolean cancelUpdate = false;
        View focusView = null;
        if (TextUtils.isEmpty(currentPassword)) {
            focusView = currentPasswordTextView;
            cancelUpdate = true;
        }
        if (TextUtils.isEmpty(newPassword)) {
            focusView = newPasswordTextView;
            cancelUpdate = true;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            focusView = confirmPasswordTextView;
            cancelUpdate = true;
        }

        if (!currentPassword.equals(password)) {
            currentPasswordTextView.setError("Mật khẩu hiện tại không đúng!");
            focusView = confirmPasswordTextView;
            cancelUpdate = true;
        }
        if (newPassword.length() < 4 || newPassword.length() > 25) {
            newPasswordTextView.setError("Mật khẩu phải từ 4 đến 25 kí tự");
            focusView = newPasswordTextView;
            cancelUpdate = true;
        }

        if (!newPassword.equals(confirmPassword)) {
            confirmPasswordTextView.setError("Mật khẩu mới không giống nhau");
            focusView = confirmPasswordTextView;
            cancelUpdate = true;
        }
        if (newPassword.equalsIgnoreCase(currentPassword)) {
            newPasswordTextView.setError("Mật khẩu mới phải khác mật khẩu hiện tại");
            focusView = newPasswordTextView;
            cancelUpdate = true;
        }

        if (cancelUpdate) {
            focusView.requestFocus();
        } else {
            String url = Constants.API_CHANGE_PASSWORD + username + "&password=" + newPassword;
            RequestSender sender = new RequestSender();
            sender.start(url, new RequestSender.IRequestSenderComplete() {
                @Override
                public void onRequestComplete(String result) {
                    Result res = ParseUtils.parseResult(result);
                    if (res.getError_code() == 200) {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("password", newPassword);
                        editor.commit();
                        openMainActivity();
                    } else if (res.getError_code() == 400) {
                        DialogUtils.showAlert(ChangePasswordActivity.this, "Tài khoản của bạn đã bị khóa", new DialogUtils.IOnOkClicked() {
                            @Override
                            public void onClick() {
                                SharedPreferences sp = getSharedPreferences("LoginState", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.clear();
                                editor.commit();
                                startMain();
                            }
                        });
                    } else {
                        DialogUtils.showAlert(ChangePasswordActivity.this, res.getError());
                    }
                }
            });
        }


    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4 || password.length() < 20;
    }

    void openMainActivity() {
        Intent intent = new Intent(this, ListRoomActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();


    }

    public void startMain() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
