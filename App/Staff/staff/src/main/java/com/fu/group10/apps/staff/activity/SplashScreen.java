package com.fu.group10.apps.staff.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.fu.group10.apps.staff.R;
import com.fu.group10.apps.staff.Utils.Utils;


/**
 * Created by QuangTV on 5/30/2015.
 */
public class SplashScreen extends Activity{

    TextView txtStatus;
    Button btnTryAgain;
    final int CHECK_TIMEOUT = 1000;
    private boolean NO_NEED_INTERNET = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_check_connection);
        txtStatus = (TextView) findViewById(R.id.txtStatus);
        btnTryAgain = (Button) findViewById(R.id.btnTryAgain);

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCheck();
            }
        });

        startCheck();
    }

    void startCheck() {

        btnTryAgain.setVisibility(View.INVISIBLE);
        txtStatus.setText(getApplicationContext().getText(R.string.checkingConnection));

        final Handler handler = new Handler();
        try {


            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (Utils.isOnline() || NO_NEED_INTERNET) {
                        openMainActivity();
                    } else {
                        btnTryAgain.setVisibility(View.VISIBLE);
                        txtStatus.setText(getApplicationContext().getText(R.string.noConnection));
                    }
                }
            }, CHECK_TIMEOUT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void openMainActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
