package com.fu.group10.capstone.apps.teachermobileapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fu.group10.capstone.apps.teachermobileapp.R;
import com.fu.group10.capstone.apps.teachermobileapp.dao.EquipmentDAO;
import com.fu.group10.capstone.apps.teachermobileapp.utils.DatabaseHelper;


/**
 * Created by QuangTV on 6/15/2015.
 */
public class AboutActivity extends Activity {

    public static final int REQUEST_CODE = 1;
    EditText txtNumber1;
    EditText txtNumber2;
    ImageView imageView;
    Button btnButton;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        db = new DatabaseHelper(this);
        txtNumber1 = (EditText) findViewById(R.id.txtNumber1);
        txtNumber2 = (EditText) findViewById(R.id.txtNumber2);
        imageView = (ImageView) findViewById(R.id.imageView);
        btnButton = (Button) findViewById(R.id.button);
//        String imageUrl = "http://pbs.twimg.com/profile_images/378800000451012500/4628fbb9dc70514d389ed9491243866f_400x400.png";
//        db.insertEquipment("test", imageUrl);

        EquipmentDAO equipment = db.getEquipments();
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(equipment.getImages(),
                0, equipment.getImages().length));


    }

    public void plus(View view) {
        int n1 = Integer.parseInt(txtNumber1.getText().toString());
        int n2 = Integer.parseInt(txtNumber2.getText().toString());

        Bundle bunlde = new Bundle();
        bunlde.putInt("number1", n1);
        bunlde.putInt("number2", n2);

        Intent intent = new Intent(AboutActivity.this, AboutActivity.class);
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
