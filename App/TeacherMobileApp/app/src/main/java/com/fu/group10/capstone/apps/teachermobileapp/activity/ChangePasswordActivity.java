package com.fu.group10.capstone.apps.teachermobileapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fu.group10.capstone.apps.teachermobileapp.R;
import com.fu.group10.capstone.apps.teachermobileapp.fragment.ProfileFragment;

import android.support.v4.app.Fragment;

/**
 * Created by QuangTV on 5/31/2015.
 */
public class ChangePasswordActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Đổi mật khẩu");
        setContentView(R.layout.activity_change);


    }




}
