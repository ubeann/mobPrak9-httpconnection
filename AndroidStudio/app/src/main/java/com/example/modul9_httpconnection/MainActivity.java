package com.example.modul9_httpconnection;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btn1, btn2, btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);

        btn1.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, URLImage.class)));
        btn2.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, URLLogin.class)));
        btn3.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, Database.class)));

    }
}