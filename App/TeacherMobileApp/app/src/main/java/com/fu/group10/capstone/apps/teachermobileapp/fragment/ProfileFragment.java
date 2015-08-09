package com.fu.group10.capstone.apps.teachermobileapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fu.group10.capstone.apps.teachermobileapp.R;
import com.fu.group10.capstone.apps.teachermobileapp.activity.ListRoomActivity;
import com.fu.group10.capstone.apps.teachermobileapp.activity.LoginActivity;
import com.fu.group10.capstone.apps.teachermobileapp.model.Result;
import com.fu.group10.capstone.apps.teachermobileapp.utils.Constants;
import com.fu.group10.capstone.apps.teachermobileapp.utils.DialogUtils;
import com.fu.group10.capstone.apps.teachermobileapp.utils.ParseUtils;
import com.fu.group10.capstone.apps.teachermobileapp.utils.RequestSender;

/**
 * Created by QuangTV on 8/9/2015.
 */
public class ProfileFragment extends Fragment {

    private EditText currentPasswordTextView;
    private EditText newPasswordTextView;
    private EditText confirmPasswordTextView;
    TextView txtStatus;
    String username;

    SharedPreferences sp;
    private String password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_change_password, container, false);
        initView(rootView);
        return rootView;
    }

    public void initView(View rootView) {
        sp = getActivity().getSharedPreferences("LoginState", getActivity().MODE_PRIVATE);
        boolean stateLogin = sp.getBoolean("LoginState", false);
        username = sp.getString("username", null);
        password =  sp.getString("password", null);
        currentPasswordTextView = (EditText) rootView.findViewById(R.id.current_password);
        newPasswordTextView = (EditText) rootView.findViewById(R.id.new_password);
        confirmPasswordTextView = (EditText) rootView.findViewById(R.id.confirm_password);

        Button btnUpdate = (Button) rootView.findViewById(R.id.change_password_button);

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
                        DialogUtils.showAlert(getActivity(), "Tài khoản của bạn đã bị khóa", new DialogUtils.IOnOkClicked() {
                            @Override
                            public void onClick() {
                                SharedPreferences sp = getActivity().getSharedPreferences("LoginState", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.clear();
                                editor.commit();
                                startMain();
                            }
                        });
                    } else {
                        DialogUtils.showAlert(getActivity(), res.getError());
                    }
                }
            });
        }


    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4 || password.length() < 20;
    }

    void openMainActivity() {
        Intent intent = new Intent(getActivity(), ListRoomActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
        getActivity().finish();


    }

    public void startMain() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
