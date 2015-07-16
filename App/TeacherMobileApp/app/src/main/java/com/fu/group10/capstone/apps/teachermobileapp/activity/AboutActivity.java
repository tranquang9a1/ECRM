package com.fu.group10.capstone.apps.teachermobileapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fu.group10.capstone.apps.teachermobileapp.R;


/**
 * Created by QuangTV on 6/15/2015.
 */
public class AboutActivity extends Activity {

    public static final int REQUEST_CODE = 1;
    EditText txtNumber1;
    EditText txtNumber2;
    Button btnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        txtNumber1 = (EditText) findViewById(R.id.txtNumber1);
        txtNumber2 = (EditText) findViewById(R.id.txtNumber2);
        btnButton = (Button) findViewById(R.id.button);



    }

    public void plus(View view) {
        int n1 = Integer.parseInt(txtNumber1.getText().toString());
        int n2 = Integer.parseInt(txtNumber2.getText().toString());

        Bundle bunlde = new Bundle();
        bunlde.putInt("number1", n1);
        bunlde.putInt("number2", n2);

        Intent intent = new Intent(AboutActivity.this, AddActivity.class);
        intent.putExtras(bunlde);
        startActivityForResult(intent, REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                Toast.makeText(AboutActivity.this, bundle.getInt("number1") + " + " + bundle.getInt("number2") + " = " + bundle.getInt("result"), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
