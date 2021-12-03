package com.example.modul9_httpconnection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.Toast;
import java.net.HttpURLConnection;
import java.net.URL;

public class URLImage extends AppCompatActivity {
    private ImageView imageView;
    private Bitmap loadedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urlimage);
        imageView = findViewById(R.id.imageView);
        String imageHttpAddress = "https://images.unsplash.com/photo-1638462798495-ece609b7cabc?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1887&q=80";
        downloadFile(imageHttpAddress);
    }

    private void downloadFile(String imageHttpAddress) {
        new Thread(() -> {
            try{
                URL imageUrl = new URL(imageHttpAddress);
                HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                conn.connect();
                loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),"Unable to load image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            messageHandler.sendEmptyMessage(0);
        }).start();
    }
    private final Handler messageHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            imageView.setImageBitmap(loadedImage);
        }
    };
}