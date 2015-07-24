package com.fu.group10.capstone.apps.staffmobileapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.fu.group10.capstone.apps.staffmobileapp.R;


/**
 * Created by QuangTV on 5/31/2015.
 */
public class ChangePasswordActivity extends Activity {

    private EditText currentPasswordTextView;
    private EditText newPasswordTextView;
    private EditText confirmPasswordTextView;
    TextView txtStatus;

    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        currentPasswordTextView = (EditText) findViewById(R.id.current_password);
        newPasswordTextView = (EditText) findViewById(R.id.new_password);
        confirmPasswordTextView = (EditText) findViewById(R.id.confirm_password);
        txtStatus = (TextView) findViewById(R.id.txtStatus);
        Intent intent = getIntent();
        password = intent.getExtras().getString("current_password");

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
        String newPassword = newPasswordTextView.getText().toString();
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
            currentPasswordTextView.setError("Current Password is incorrect!");
            focusView = confirmPasswordTextView;
            cancelUpdate = true;
        }
        if (!newPassword.equals(confirmPassword)) {
            currentPasswordTextView.setError("New password not match!");
            focusView = confirmPasswordTextView;
            cancelUpdate = true;
        }

        if (cancelUpdate) {
            focusView.requestFocus();
        } else {
            openMainActivity();
        }


    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4 || password.length() < 20;
    }

    void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
