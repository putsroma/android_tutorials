package com.example.httprequest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class SecondaryActivity extends AppCompatActivity {

    private TextView artistName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);

        artistName = findViewById(R.id.textView2nd);
        Bundle extras = getIntent().getExtras();

        if(extras == null) {
            Log.i("empty position", "error while fetching hot100 array");
        } else {
            artistName.setText(extras.getString("com.android.Hot100Artists"));
        }
    }
}
